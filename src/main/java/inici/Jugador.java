/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inici;
import altres.*;
import java.util.ArrayList;
/**
 *
 * @author Fernando
 */
public class Jugador {

    private String nom;
    private int puntsAtac;
    private int puntsDefensa;
    private int vides;
    private Equip equip;
    private ArrayList<Poder> poders = new ArrayList<Poder>();
    static ArrayList<Jugador> llista = new ArrayList<Jugador>();
    static int cantidadVidas = 200;

    // Constructor
    public Jugador(String nom, int puntsAtac, int puntsDefensa, int vides) {
        this.nom = nom;
        this.puntsAtac = puntsAtac;
        this.puntsDefensa = puntsDefensa;
        this.vides = vides;
     
    }
    // Getters

    public String getNom() {
        return nom;
    }

    public int getPuntsAtac() {
        return puntsAtac;
    }

    public int getPuntsDefensa() {
        return puntsDefensa;
    }

    public int getVides() {
        return vides;
    }

    public Equip getEquip() {
        return equip;
    }

    public ArrayList<Poder> getPoders() {
        return poders;
    }
    

    // Setters

    protected void setNom(String nom) {
        this.nom = nom;
    }

    protected void setPuntsAtac(int puntsAtac) {
        this.puntsAtac = puntsAtac;
    }

    protected void setPuntsDefensa(int puntsDefensa) {
        this.puntsDefensa = puntsDefensa;
    }

    protected void setVides(int vides) {
        this.vides = vides;
    }

    public void setEquip(Equip equip) {

        if (this.getEquip() != null) {
            equip.llevar(this);
        } else {
            equip.posa(this);

        }
        
    }

    public void setPoders(ArrayList<Poder> poders) {
        this.poders = poders;
    }


    // Metodo ataca
    public void ataca(Jugador jugador) throws AtacAMortException, AtacEllMateixException{

        if (this instanceof Alien) {
            ((Alien) this).enloquecer();
        }

        System.out.println("ABANS DE L'ATAC");
        System.out.println("Atacant: " + this.toString());
        System.out.println("Atacat: " + jugador.toString());

        System.out.println("ATAC");
        
        int BonoAtaqueThis = 0;
        int BonoAtaqueJugador = 0;
        int BonoDefensaThis = 0;
        int BonoDefensaJugador = 0;
        
        for (Poder poder : this.poders) {
            BonoAtaqueThis += poder.getBonusAtac();
            BonoDefensaThis += poder.getBonusDefensa();
        }
        
        for (Poder poder : jugador.poders) {
            BonoAtaqueJugador += poder.getBonusAtac();
            BonoDefensaJugador += poder.getBonusDefensa();
        }
        
        if(this.getVides()<=0 || jugador.getVides()<=0){
            throw new AtacAMortException();
        }
        
        if(this.getNom().equals(jugador.getNom())){
            throw new AtacEllMateixException();
        }
        
        jugador.esColpejatAmb(this.puntsAtac,BonoAtaqueThis,BonoDefensaThis);
        this.esColpejatAmb(jugador.puntsAtac,BonoAtaqueJugador,BonoDefensaJugador);

        System.out.println("DESPRÉS DE L'ATAC");
        System.out.println("Atacant: " + this.toString());
        System.out.println("Atacat: " + jugador.toString());
        
    }

    // Metodo esColpejatAmb
    // Pep Garcia és colpejat amb 27 punts i es defén amb 8. Vides: 39 - 19 = 20
    protected void esColpejatAmb(int quantitat, int bonoAtaque,int bonoDefensa) {

        int ataque = (quantitat+bonoAtaque) - (puntsDefensa+bonoDefensa);
        int videsAnteriors = this.getVides();

        if (ataque > 0 && !(this instanceof Guerrer)) {
            this.setVides(this.getVides() - ataque);

        } else if (ataque >= 5 && this instanceof Guerrer) {
            this.setVides(this.getVides() - ataque);

        }

        System.out.println(nom + " és colpejat amb " + quantitat + " punts i es defén amb " + puntsDefensa + ". Vides: " + videsAnteriors + " - " + ataque + "= " + vides);

    }

    //toString
    @Override
    public String toString() {
        
        String tipo = "";
        
        if(this instanceof Guerrer){
            tipo = "Guerrer";
        }else if(this instanceof Alien){
            tipo = "Alien";
        }else if(this instanceof Huma){
            tipo = "Huma";
        }
        
        return nom + "(" + tipo + ", PA:" + puntsAtac + ", PD:" + puntsDefensa + ", PV:" + vides +")" ;
    }

    public boolean equals(Jugador player) {
        if (player == this) {
            return true;
        }
        if (!(player instanceof Jugador)) {
            return false;
        }
        Jugador jugador = (Jugador) player;
        return nom.equals(jugador.nom);
    }

    // Metodo posa
    public void posa(Poder poder) {

        if (!this.poders.contains(poder)) {
            this.poders.add(poder);

        }

    }

    public void llevar(Poder poder) {
        if (this.poders.contains(poder)) {
            this.poders.remove(poder);
        }
    }
    //Menu

    public static void menu() {
        int opcion = -1;
        
        while(opcion!=0){
            inici.PereiraFernando_JocDeRol.printMenuTitle("JUGADORS");
            System.out.println("1.Crear");
            System.out.println("2.Consultar");
            System.out.println("3.Eliminar");
            System.out.println("4.Assignar a equip");
            System.out.println("5.Llevar d'equip");
            System.out.println("6.Assignar poder");
            System.out.println("0.Eixir");
            
            opcion = teclat.Teclat.lligInt("Introduce la opcion: ");
            
            switch (opcion) {
                case 1 -> crear();
                case 2 -> consultar();
                case 3 -> eliminar();
                case 4 -> assignarEquip();
                case 5 -> llevarEquip();
                case 6 -> assignarPoder();
                default -> {
                }
            }
        }
    }

    public static void crear() {
        char tipoJugador = teclat.Teclat.lligChar("Introduce el tipo de jugador", "HGA");
        String nom = teclat.Teclat.lligString("Introduce el nombre: ");
        int puntsAtac = teclat.Teclat.lligInt("Introduce los puntos de ataque: ", 1, 100);
        int puntsDefensa = Math.abs(puntsAtac - 100);
        
        Jugador nuevoJugador = null;
        
        switch (tipoJugador) {
            case 'H' -> nuevoJugador = new Huma(nom, puntsAtac, puntsDefensa, cantidadVidas);
            case 'G' ->
                nuevoJugador = new Guerrer(nom, puntsAtac, puntsDefensa, cantidadVidas);
            case 'A' ->
                nuevoJugador = new Alien(nom, puntsAtac, puntsDefensa, cantidadVidas);
            default -> {
            }
        }
        boolean encontrado = false;
        for (Jugador jugador : llista) {
            if (jugador.getNom().equals(nuevoJugador.getNom())) {
                encontrado = true;
            }
        }

        if (encontrado) {
            System.out.println("El jugador ya existe");
        } else {
            llista.add(nuevoJugador);
            System.out.println("El jugador se ha creado correctamente");
        }
    }

    public static void consultar() {

        if (llista.isEmpty()) {
            System.out.println("No hay jugadores creados");
        } else {
            System.out.println("Lista de jugadores:");
            for (Jugador jugador : llista) {
                System.out.println(jugador);
            }
        }
    }

    public static void eliminar() {

        String nom = teclat.Teclat.lligString("Introduce el nombre: ");
        boolean Noeliminado = true;

        for (Jugador jugador : llista) {
            if(jugador.getNom().equals(nom)){
                llista.remove(jugador);
                System.out.println("Se ha eliminado el jugador correctamente");
                Noeliminado = false;
                break;
            }
        }
        
        if(Noeliminado){
            System.out.println("No se ha encontrado al jugador");
        }

    }

    public static void assignarEquip() {
        String nom = teclat.Teclat.lligString("Introduce el nombre del jugador: ");
        String nomEquip = teclat.Teclat.lligString("Introduce el nombre del equipo: ");
        
        Jugador jugador = null;
        Equip equip = null;
        
        for(int i=0;i<llista.size();i++){
            jugador = llista.get(i);
            if(jugador.getNom().equals(nom)){
                break;
            }else{
                jugador = null;
            }
        }
        
        for(int i=0;i<Equip.llista.size();i++){
            equip = Equip.llista.get(i);
            if(equip.getNom().equals(nomEquip)){
                break;
            }else{
                equip = null;
            }
        }

        if (equip != null && jugador != null) {
            equip.posa(jugador);
            System.out.println("El jugador se ha asignado al equipo correctamente");
        }else{
            System.out.println("El jugador o el equipo no existen");
        }
    }

    public static void assignarPoder() {
        String nom = teclat.Teclat.lligString("Introduce el nombre del jugador: ");
        String poderNombre = teclat.Teclat.lligString("Introduce el nombre del poder: ");

        try {
            int indiceJugador = llista.indexOf(nom);

            Jugador jugador = llista.get(indiceJugador);

            int indicePoder = Poder.llista.indexOf(poderNombre);

            Poder poder = Poder.llista.get(indicePoder);

            jugador.poders.add(poder);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("El jugador o el poder no existen");
        }
    
    }
    
    public static void llevarEquip(){
                String nom = teclat.Teclat.lligString("Introduce el nombre del jugador: ");
        String nomEquip = teclat.Teclat.lligString("Introduce el nombre del equipo: ");
        
        Jugador jugador = null;
        Equip equip = null;
        
        for(int i=0;i<llista.size();i++){
            jugador = llista.get(i);
            if(jugador.getNom().equals(nom)){
                break;
            }else{
                jugador = null;
            }
        }
        
        for(int i=0;i<Equip.llista.size();i++){
            equip = Equip.llista.get(i);
            if(equip.getNom().equals(nomEquip)){
                break;
            }else{
                equip = null;
            }
        }

        if (equip != null && jugador != null) {
            equip.llevar(jugador);
            System.out.println(" El jugador se ha eliminado del equipo correctamente");
        }else{
            System.out.println("El jugador o el equipo no existen");
        }
    }

}
