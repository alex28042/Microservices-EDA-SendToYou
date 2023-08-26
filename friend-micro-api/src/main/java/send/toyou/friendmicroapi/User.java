package send.toyou.friendmicroapi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class User implements Serializable{
    private String name, email, direccion, username, pwd;
    LocalDate fecha_nac;
    ArrayList<String> friends;
    private Long id;
    private static long id_cont = 0;

    public User(String name, String email, String direccion, LocalDate fecha_nac, String username, String pwd) {
        this.name = name;
        this.email = email;
        this.direccion = direccion;
        this.fecha_nac = fecha_nac;
        this.username = username;
        this.pwd = pwd;
        friends = new ArrayList<>();// USAR FRIENDS AT (AMIGOS QUE YA EXISTAN)   MEJOR ARRAY DE STRINGS usando username como clave primaria

        id_cont++;
        id= id_cont;
    }

    //cargando el usuario desde el archivo de texto
    public User(String linea){
        //creamos array de string en el que se van a almacenar las variables
        String[] campos = linea.split(":");
        //Igualamos el valor de la seccion a la variable local
        name = campos[0];
        email = campos[1];
        direccion = campos[2];
        fecha_nac = LocalDate.parse(campos[3], DateTimeFormatter.ofPattern("yyyy-dd-MM"));
        username = campos[4];
        pwd = campos[5];
        String linea2 = campos[6];
        String[] amigos = linea2.split(", ");
        if (friends ==null){
            friends =new ArrayList<>();
            friends.add("BUG");
        }
        Collections.addAll(friends, amigos);
    }
    public ArrayList<String> getFriends() {
        return friends;
    }

    public String getUsrName(){
        return username;
    }
    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd(){
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return name+":"+email+":"+direccion+":"+fecha_nac+":"+username+":"+pwd+/*":"+usrdao.listarAmigos(username)+*/":"+friendsToString(friends.toString());
    }
    private String friendsToString(String input){
        String[] elements = input.substring(1, input.length() - 1).split(", ");
        StringBuilder output = new StringBuilder();
        for (int i = 1; i < elements.length; i++) {
            output.append(elements[i]);
            if (i < elements.length - 1) {
                output.append(", ");
            }
        }
        return output.toString();
    }

    public boolean isFriend(String username) {
        return friends.contains(username);
    }
    public void setFriends(String amigo) {
        if (!isFriend(amigo))friends.add(amigo);
    }
    public void removeFriend(String amigo) {
        friends.remove(amigo);
    }
    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public void setUsrname(String usrname) {
        this.username=usrname;
    }
}