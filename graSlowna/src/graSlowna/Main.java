/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graSlowna;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author marty
 */
public class Main {
    private char[] target;

    public static void main(String[] args) {
        Main main=new Main();
        main.szukanieSlowa();
    }

    public Main() {

        // SLOWO DO ZNALEZIENIA
        String str="martynakotlowska";
        //zamiana słowa na ciąg bytów
        char[] ch = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        target=ch;
    }

    public void szukanieSlowa(){
        Instant start;
        //wstawienie znacznika czasu startu
        start=Instant.now();
        char[] parent=generacjaRodzica(target.length);
        int bestFitnes=fitnes(parent);
        int getFitnes=0;

        while(getFitnes<target.length){
            char [] child=mutacja(parent);
            getFitnes= fitnes(child);
            if (bestFitnes>=getFitnes)
                {}
            else{
            //wyswietlenie znalezionego kolejnego slowa, jego fitnesu, i czasu po jakim został znaleziony
            System.out.println(wysSlowo(child)+"   "+getFitnes+"          "+zlicznieRoznicy(start,Instant.now()));
            if (getFitnes>=parent.length)
                break;
            bestFitnes=getFitnes;
            parent=child;
            }
        }
    }

//generowanie rodzica o określonej długości z setList (określonych znaków)
public char[] generacjaRodzica(int dlugosc){
    char [] parent=new char[dlugosc];
    for (int i=0;i<dlugosc;i++) {
        parent[i]=losowanieZnakuZZestawuZnakow();
    }
    System.out.println("Wygenerowany rodzic :\n"+wysSlowo(parent));
    return parent;
}


// funkcja tworząca słowo ze znaków w tablicy char
public String wysSlowo(char[] tab){
        StringBuilder stringBuilder=new StringBuilder();
        for(char i:tab)
        {
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
}
//losowanie znaków z zestawu znaków dostępnych w stringu setList
    public char losowanieZnakuZZestawuZnakow(){
        Random random=new Random();
        String setList = " aąbcćdeęfghijklłmnoópqrsśtuvwxyzźżABCĆDEFGHIJKLMNOÓPQRSŚTUVWXYZŹŻ!.";
        char litera = setList.charAt(random.nextInt(setList.length()));
        return litera;
    }

    //mutacja litery w chromosomie
    public char[] mutacja(char[] chromosom1){
        char[] template=copyTable(chromosom1);
        int fit=fitnes(template);
        Random p=new Random();
        int fitnes= fitnes(chromosom1);
        //wykorzystanie pętli doWhile jako zabezpieczenie aby nie generowano wartości losowej
        // dla będącej już na swoim miejscu wartości. Losowanie indeksu odbywa się do momentu, aż zostanie wylosowany punkt który
        do{
            chromosom1=copyTable(template);
            chromosom1[p.nextInt(chromosom1.length)]=losowanieZnakuZZestawuZnakow();
        }while(fitnes(chromosom1)<fit);

        return chromosom1;
    }

    //funkcja tworząca kopię wartości tablicy
    public char[] copyTable(char [] toCopy){
        char[] copied=new char[toCopy.length];
        for (int i=0;i<toCopy.length;i++)
            copied[i]=toCopy[i];
        return copied;
    }

    //funkcja fitness zwraca zgadzające się znaki dla słowa szukanego
    // i wylosowanego
    public int fitnes(char[] sprawdzaneSlowo){
        int licznik=0;
        //zwiekszanie licznika jezeli w poszukiwanym slowie jest
        // taka sama litera na tym samym miejscu
        for(int i=0;i<sprawdzaneSlowo.length;i++){
        if (sprawdzaneSlowo[i]==target[i])
            licznik++;
        }
    return licznik;
    }




    //Funkcja zliczająca różnice czasu między dwiema instancjami czasu, wyliczana w milisekundach
    public String zlicznieRoznicy(Instant one,Instant two){

        Long milis=ChronoUnit.MILLIS.between(one,two);
        StringBuilder stringBuilder=new StringBuilder();
        //wylicza ilosc pelnych godzin
        if (TimeUnit.MILLISECONDS.toHours(milis)==0)
            stringBuilder.append(0);
        else
            stringBuilder.append(TimeUnit.MILLISECONDS.toHours(milis)%60);
        stringBuilder.append(":");
        //wylicza ilosc pelnych minut
        if (TimeUnit.MILLISECONDS.toMinutes(milis)==0)
            stringBuilder.append(0);
        else
            stringBuilder.append(TimeUnit.MILLISECONDS.toMinutes(milis)%60);
        stringBuilder.append(":");
        //wylicza ilosc pelnych sekund
        if (TimeUnit.MILLISECONDS.toSeconds(milis)==0)
            stringBuilder.append(0);
        else
            stringBuilder.append(TimeUnit.MILLISECONDS.toSeconds(milis)%60);
        stringBuilder.append(".");
        //przekształcenie wartości milisekund do części sekundy
        if ((milis%1000)<10)
            stringBuilder.append("00");
        if((milis%100)<100&&(milis%1000)>10)
        {
            stringBuilder.append("0");
        }
        stringBuilder.append(""+milis%1000);

        return stringBuilder.toString();
    }
}
