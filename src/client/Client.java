package client;

import protocol.Action;
import protocol.Answer;
import protocol.Request;
import protocol.Result;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static public Request createRequest() {
        System.out.println("Veuillez selectionner l'action à faire :\n1. Ajout d'un nom.\n" +
                "2. Ajout d'un surnom.\n3. Recherche de surnoms.\n4. Rechercher un nom à partir d'un surnom\n" +
                "5. Supprimer un surnom.\n6. Supprimer un nom.");

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
        } else if ("3".equals(number)) {
            System.out.println("Veuillez tapper le Nom dont vous cherchez le surnom :");
            in = new Scanner(System.in);

            String name = in.nextLine();
            in.close();
            return createRequest(Action.GET_NICKNAMES, name);
        } else if ("4".equals(number)) {
            System.out.println("Veuillez tapper le Surnom dont vous cherchez le nom originel :");
            in = new Scanner(System.in);

            String surname = in.nextLine();
            in.close();
            return createRequest(Action.GET_NAME, surname);
        } else if ("5".equals(number)) {
            System.out.println("Veuillez tapper le Surnom à effacer :");
            in = new Scanner(System.in);

            String surname = in.nextLine();
            in.close();
            return createRequest(Action.REMOVE_NICKNAME, surname);
        } else if ("6".equals(number)) {
            System.out.println("Veuillez tapper le nom à effacer :");
            in = new Scanner(System.in);

            String name = in.nextLine();
            in.close();
            return createRequest(Action.REMOVE_NAME, name);
        } else{
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

    /**
     * Affichage de l'action réalisé par le Serveur.
     * @param answer
     */
    static public void showAction(Answer answer) {
        if (answer.getAction() == Action.ADD_NAME) {
            System.out.println("L'action réalisé est l'Ajout d'un Nom.");
        } else if (answer.getAction() == Action.ADD_NICKNAME) {
            System.out.println("L'action réalisé est l'Ajout d'un Surnom.");
        } else if (answer.getAction() == Action.GET_NAME) {
            System.out.println("L'action réalisé est la Récupération d'un Nom en tappant un Surnom.");
        } else if (answer.getAction() == Action.GET_NICKNAMES) {
            System.out.println("L'action réalisé est la Récupération de tout les surnoms d'un Nom donné.");
        } else if (answer.getAction() == Action.REMOVE_NAME) {
            System.out.println("L'action réalisé est la suppréssion d'un nom.");
        } else if (answer.getAction() == Action.REMOVE_NICKNAME) {
            System.out.println("L'action réalisé est la suppréssion d'un surnom.");
        } else {
            System.err.println("L'action réalisé n'est pas reconnu...");
        }
    }

    /**
     * Affichage des valeurs de la réponse du Serveur.
     * @param answer
     */
    static public void showValues(Answer answer) {
        if (answer.getAction() == Action.ADD_NAME) {
            System.out.println("L'ajout du Nom : " + answer.getFirstValue()+ " a bien été réalisé.");
        } else if (answer.getAction() == Action.ADD_NICKNAME) {
            System.out.println("L'ajout du Surnom : " + answer.getFirstValue()+ " qui est un surnom" +
                    " de : " + answer.getSecondValue() + " a bien été réalisé.");
        } else if (answer.getAction() == Action.GET_NAME) {
            System.out.println("La recherche du Nom associé au Surnom : " + answer.getFirstValue()+ " a retourné" +
                    " le Nom suivant : " + answer.getSecondValue() + ".");
        } else if (answer.getAction() == Action.GET_NICKNAMES) {
            System.out.println("La recherche des Surnoms associé au Nom: " + answer.getFirstValue()+ " a retourné" +
                    " les Surnoms suivant : " + answer.getSecondValue() + ".");
        } else if (answer.getAction() == Action.REMOVE_NAME) {
            System.out.println("La suppréssion du Nom : " + answer.getFirstValue()+ " s'est bien déroulé sans" +
                    " problèmes.");
        }else if (answer.getAction() == Action.REMOVE_NICKNAME) {
            System.out.println("La suppréssion du Surnom : " + answer.getFirstValue()+ " s'est bien déroulé sans" +
                    " problèmes.");
        }
    }

	public static void main(String[] args) {
		Socket socket = null;

		ObjectOutputStream os = null; // output stream
		ObjectInputStream is = null; // input stream

		try {
			socket = new Socket("10.212.96.252", 1313);
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
                showAction(a);
                showValues(a);
            } else if ("nope". equals(tavu)) {
                System.out.println("Le serveur est pas gentil, il a refusé notre requète...");
            } else {
                System.out.println("Dear, we're in trouble !");
            }
			
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("Youston, we have a problem ! Voici le problème en question : " + e);
		}
	}
}
