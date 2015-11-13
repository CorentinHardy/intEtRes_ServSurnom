package client;

import protocol.Action;
import protocol.Answer;
import protocol.Request;
import protocol.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    static public Request createRequest() {
        System.out.println("Veuillez selectionner l'action à faire :\n1. Ajout d'un nom.\n" +
                "2. Ajout d'un surnom.");

        Scanner in = new Scanner(System.in);

        String number = in.nextLine();

        if ("1".equals(number)) {
            System.out.println("Veuillez tapper le Nom à ajouter :");
            in = new Scanner(System.in);

            String name = in.nextLine();
            in.close();
            return createRequest(Action.ADD_NAME, name);
        } else if ("2".equals(number)) {
            System.out.println("Veuillez tapper le Nom auquel vous voulez ajouter un surnom :");
            in = new Scanner(System.in);

            String name = in.nextLine();
            System.out.println("Veuillez tapper le Surnom à ajouter :");
            in = new Scanner(System.in);

            String nickname = in.nextLine();
            in.close();
            return createRequest(Action.ADD_NICKNAME, name, nickname);
        } else {
            in.close();
            return error();
        }
    }

    static public Request error() {
        System.out.println("Veuillez choisir une valeur entre 1 et 2");
        return createRequest();
    }

    /**
     * Créer une requète qui prendra un seul string (généralement ADD_NAME)
     * @param action
     * @param string
     * @return
     */
    static public Request createRequest(Action action, String string) {
        return new Request(action, string);
    }

    /**
     * Créer une requète qui prendra deux string (généralement ADD_NICKNAME)
     * @param action Action
     * @param string1 Nom
     * @param string2 Surnom
     * @return
     */
    static public Request createRequest(Action action, String string1, String string2) {
        return new Request(action, string1, string2);
    }

    static public String checkAnswer(Answer answer) {
        Result result = answer.getResult();
        if (result == Result.OK) {
            return "ok";
        } else {
            return "nope";
        }
    }

	public static void main(String[] args) {
		Socket socket = null;

		ObjectOutputStream os = null; // output stream
		ObjectInputStream is = null; // input stream

		try {
			socket = new Socket("10.212.102.235", 4321);
            System.out.println("lel");

			Request r = createRequest();
			
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			
			Answer a = null;

			os.writeObject(r);
			while(a == null){
				a = (Answer) is.readObject();
			}

            String tavu = checkAnswer(a);
			if ("ok".equals(tavu)) {
                System.out.println("tavu, on a l'Answer et ça marche mon loulou ! Et elle nous dit un bon gros OK !" +
                        "\nMaintenant, regardons ce qu'on nous à retourné...\nDonc, on a déjà réalisé l'action" +
                        " : " + a.getAction() + "\nEt avec cette action, on a tripoté ça : " + a.getFirstValue());
            } else if ("nope". equals(tavu)) {
                System.out.println("Ohlala, on a fait de la mierda mon ami ><'. RIP notre vie...");
            } else {
                System.out.println("Dear, we're in trouble !");
            }
			
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("Tu la sens mon Exception, gros ! D'ailleurs, la voici, gros : " + e);
		}
	}
}
