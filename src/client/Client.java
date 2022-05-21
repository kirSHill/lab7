package client;

//import server.commands.ExecuteScript;
import essentials.interaction.*;
import essentials.precommands.Precommand;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {
   private static final UserInteraction interaction = new ConsoleInteraction();
   private static ServerInteraction serverInteraction;
   private static int port;
   private static String ip;
   private static Socket socket;
   private static final Logger log = Logger.getLogger(Client.class.getName());
   private static int count;
   private static boolean isAuthorized = false;

   public static void main(String[] args) throws InterruptedException {
      try {
         String[] argument = args[0].split(":");
         if (argument.length != 2) {
            throw new Exception();
         }
         ip = argument[0];
         port = Integer.parseInt(argument[1]);
      } catch (Exception e) {
         log.info("Неверно указан адрес и/или порт. Введите в формате '*.*.*.*:????'");
         return;
      }
      connect();
      run();
   }
   public static void connect() throws InterruptedException {
      while (true) {
         try {
            log.info("Подключение к серверу.");
            socket = new Socket(ip, port);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            serverInteraction = new ServerInteraction(inputStream, outputStream);
            return;
         } catch (IOException e) {
            count++;
            log.info("Неудачное подключение к серверу " + e.getMessage());
            Thread.sleep(2000);
            if (count == 5) {
               interaction.print(true, "Кажется, сервер умер. Хотите подключиться ещё раз? Введите 'да' или 'нет'.");
               String reconnect = interaction.getData();
               if(reconnect.equals("да")) {
                  count = 0;
                  return;
               } else if(reconnect.equals("нет")) {
                  log.info("Завершение работы клиента.");
                  return;
               } else {
                  interaction.print(true, "Введите 'да' или 'нет'!");
               }
            }
         }
      }
   }

   public static void run() {
      boolean run = true;
      boolean reconnect = false;
      while (run) {
         try {
            if (reconnect | socket.isClosed() | !socket.isConnected()) {
               isAuthorized = false;
               connect();
               reconnect = false;
            }
            Precommand precommand;
            if (!isAuthorized) {
               interaction.print(false, "Введите '1', если хотите пройти регистрацию, или '2', если хотите авторизоваться. ");
               String input = interaction.getData();
               if (Objects.equals(input,"1")) {
                  precommand = Precommander.getCommand("register", false, interaction);
               } else if (Objects.equals(input,"2")) {
                  precommand = Precommander.getCommand("authorize",false,interaction);
               } else {
                  continue;
               }
            } else {
               interaction.print(false, "\nВведите команду: ");
               String potencialCommand = interaction.getData();
               if (potencialCommand == null) {
                  continue;
               }
               precommand = Precommander.getCommand(potencialCommand, false, interaction);
            }
               if (precommand == null) {
                  continue;
               }
               if (precommand.getCommandName().equals("exit")) {
                  return;
               }
               if (precommand.getCommandName().equals("executeScript")) {
                  boolean result = true;
                  File file = new File((String) precommand.getArgument());
                  Scanner scanner;
                  try {
                     scanner = new Scanner(file);
                  } catch (FileNotFoundException e) {
                     interaction.print(true, "Введённого Вами файла не существует!");
                     continue;
                  }
                  int number = 1;
                  while (scanner.hasNextLine()) {
                     String line = scanner.nextLine();
                     if (line.trim().isEmpty()) {
                        continue;
                     }
                     try {
                        ScriptInteraction scriptInteraction = new ScriptInteraction(scanner);
                        Precommand precommand1 = Precommander.getCommand(line, true, scriptInteraction);
                        if (precommand1 == null) {
                           continue;
                        }
                        if (precommand1.getCommandName().equals("exit")) {
                           return;
                        }
                        serverInteraction.sendData(precommand1);
                        Message message = (Message) serverInteraction.readData();
                        if (!message.isSuccessful()) {
                           throw new Exception();
                        }
                     } catch (Exception e) {
                        log.info("Ошибка при выполнении строки " + number);
                        result = false;
                        break;
                     }
                     number++;
                  }
                  if (result) {
                     log.info("Команды выполнились успешно!");
                  }
                  continue;
               }
               try {
                  serverInteraction.sendData(precommand);
               } catch (IOException e) {
                  log.info("Ошибка. " + e.getMessage());
                  reconnect = true;
                  continue;
               }
               Message message;
               try {
                  message = (Message) serverInteraction.readData();
               } catch (Exception e) {
                  log.info("Ошибка." + e.getMessage());
                  reconnect = true;
                  continue;
               }
               interaction.print(true, message.getText());
               if (!message.isSuccessful()) {
                  run = false;
               } else if (Objects.equals(precommand.getCommandName(),"authorize")) {
                  isAuthorized = true;
                  interaction.print(true,"Для просмотра полного списка команд введите 'help'.");
               }
         } catch (Exception e) {
            log.info("Ошибка." + e.getMessage());
         }
      }
   }
}