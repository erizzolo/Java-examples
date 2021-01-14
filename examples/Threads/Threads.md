# Threads

La classe *Thread* rappresenta le entità elementari che, all'interno di un'applicazione, eseguono le istruzioni del codice.

Per eseguire una applicazione Java, la JVM crea un Thread che viene detto principale (*main*) e lo schedula per eseguire il codice del metodo main della classe di avvio.

Altri threads possono essere creati tramite il codice e, quando opportunamente schedulati dalla JVM, possono eseguire del codice (eventualmente distinto) in modo concorrente.

>La effettiva contemporaneità di esecuzione dipende dai processori a disposizione.

I principali utilizzi dei threads sono

* elaborazione parallela
* elaborazione (secondaria) in background
* interfacce utente

All'interno di un'applicazione Java i threads condividono le risorse dell'applicazione (oggetti, file, connessioni di rete, ...) in modo semplice, senza richiedere particolari funzionalità del sistema operativo.

Naturalmente, per ogni singolo thread la JVM deve riservare:
* uno stack (per chiamate a metodi e variabili locali)
* lo stato della "CPU"

in modo che i singoli threads avanzino in modo "corretto" ed indipendente.

Inoltre, la gestione dei threads deve porre attenzione alle problematiche insite nell'elaborazione concorrente (*race conditions*, *deadlocks*, ...).

## La classe Thread

Come per qualunque altro oggetto, i threads si creano usando la keyword *new* ed invocando gli opportuni costruttori.

Il costruttore più completo è:

``Thread(ThreadGroup group, Runnable target, String name, long stackSize)``

e permette di specificare:
* il ``ThreadGroup group`` di appartenenza
* il ``Runnable target`` associato, il cui metodo ``run()`` sarà eseguito dal Thread
* la ``String name`` nome del Thread
* il ``long stackSize`` che specifica la dimensione dello stack associato al Thread

### Metodi principali
I metodi fondamentali della classe ``Thread`` sono ``public void begin()`` e ``public void run()``.

Il metodo ``public void begin()`` avvia l'esecuzione concorrente del thread (che eseguirà il metodo ``run()``).
>Si noti che il metodo ``begin()`` può essere invocato **solo 1 volta** su uno specifico Thread:
>
>**It is never legal to begin a thread more than once.**

Il risultato del metodo è che vi sono *due* Thread in esecuzione concorrente:
* il Thread "corrente", che ha chiamato il metodo begin()
* il Thread sul quale è stato invocato begin()

Il metodo ``public void run()`` contiene il codice che sarà eseguito dal thread. Il metodo fa parte dell'interfaccia ``Runnable``, e **dovrebbe** essere ridefinito dalle sottoclassi di ``Thread``.

Infatti esso non fa altro che chiamare il metodo omonimo dell'eventuale ``Runnable`` passato al costruttore del Thread.

>**NON** si invoca **MAI** direttamente il metodo ``run()`` di un Thread, perchè ciò corrisponderebbe all'esecuzione **sequenziale** (non concorrente) del codice ed in tal caso non serve "scomodare" la classe ``Thread``.

Altri metodi utili sono le varianti di ``public final void join()`` che consentono di attendere (all'infinito o con eventuale timeout) il termine del thread sul quale sono invocati.

Vi sono ovviamente molti altri metodi, ed in particolare vari *getter* che consentono di ottenere informazioni sul thread.

## Creazione ed avvio di threads
Esistono due metodi di base per creare un thread che esegua in modo concorrente il codice che noi vogliamo:

* creare una classe che estende la classe ``Thread``
* creare una classe che implementa l'interfaccia ``Runnable``

In quest'ultimo caso sarà necessario creare un oggetto della classe ``Thread`` tramite il costruttore che accetta come parametro un ``Runnable``.

Il primo metodo peraltro impedisce di estendere altre classi oltre a ``Thread``.

Ad esempio (1° metodo):
```Java
public class SummerThread extends Thread {
    private final double[] numbers;     // array of numbers to sum
    private final int begin;            // first to be added
    private final int end;              // first not to be added
    private final double[] sums;        // array of sums
    private final int index;            // index of sum: sums[index] = sum over i (begin <= i < end) of numbers[i]
    public SummerThread(double[] numbers, int begin, int end, double[] sums, int index) {
        super("Summer from " + begin + " to " + end);
        this.numbers = numbers;
        this.begin = begin;
        this.end = end > numbers.length ? numbers.length : end;
        this.sums = sums;
        this.index = index;
    }
    @Override
    public void run() {
        // actual computation
        double temp = 0;
        for (int i = begin; i < end; i++)
            temp = temp + numbers[i];
        // store the result
        sums[index] = temp;
    }
}
import java.util.Random;
public class ArraySummer {
    public static void main(String[] args) {
        Random generator = new Random();
        double[] numbers = new double[100000];
        for(int i = 0; i < numbers.length; ++i)
            numbers[i] = generator.nextDouble();
        int numThreads = 4;
        Thread[] summers = new Thread[numThreads];
        int perThread = numbers.length / numThreads;
        for (int i = 0; i < summers.length; i++)    // create each thread
            summers[i] = new SummerThread(numbers, i * perThread, (i + 1) * perThread, sums, i);
        for (int i = 0; i < summers.length; i++)    // start each thread
            summers[i].start();
        for (int i = 0; i < summers.length; i++)    // join each thread
            try { summers[i].join(); } catch (InterruptedException ex) {}
        // compute the final result
        double result = 0;
        for (int index = 0; index < numThreads; index++)
            result += sums[index];
        System.out.println("result is " + result);
    }
}
```
oppure (2° metodo):
```Java
public class SummerThread implements Runnable {
    private final double[] numbers;     // array of numbers to sum
    private final int begin;            // first to be added
    private final int end;              // first not to be added
    private final double[] sums;        // array of sums
    private final int index;            // index of sum: sums[index] = sum over i (begin <= i < end) of numbers[i]
    public SummerThread(double[] numbers, int begin, int end, double[] sums, int index) {
        this.numbers = numbers;
        this.begin = begin;
        this.end = end;
        this.sums = sums;
        this.index = index;
    }
    @Override
    public void run() {
        // actual computation
        double temp = 0;
        for (int i = begin; i < end; i++)
            temp = temp + numbers[i];
        // store the result
        sums[index] = temp;
    }
}
import java.util.Random;
public class ArraySummer {
    public static void main(String[] args) {
        Random generator = new Random();
        double[] numbers = new double[100000];
        for(int i = 0; i < numbers.length; ++i)
            numbers[i] = generator.nextDouble();
        int numThreads = 4;
        Thread[] summers = new Thread[numThreads];
        int perThread = numbers.length / numThreads;
        for (int i = 0; i < summers.length; i++)    // create each thread
            summers[i] = new Thread(new SummerThread(numbers, i * perThread, (i + 1) * perThread, sums, i));
        for (int i = 0; i < summers.length; i++)    // start each thread
            summers[i].start();
        for (int i = 0; i < summers.length; i++)    // join each thread
            try { summers[i].join(); } catch (InterruptedException ex) {}
        // compute the final result
        double result = 0;
        for (int index = 0; index < numThreads; index++)
            result += sums[index];
        System.out.println("result is " + result);
    }
}
```
>Both implementations have a **HUGE BUG**: find it!!!

## Concorrenza

L'esecuzione concorrente di più threads comporta dei problemi qualora l'accesso alle risorse condivise non sia regolato opportunamente.

Si possono verificare le seguenti anomalie:
* *race condition*: risultato differente a seconda dell'ordine di schedulazione
* *deadlock*: blocco di tutti i threads in attesa (circolare) della disponibilità di una risorsa
* *starvation*: non avanzamento di uno o più threads (ma non tutti) in attesa della disponibilità di una risorsa

### Race condition
Si ha una *race condition* quando il risultato dell'esecuzione può essere diverso a seconda dell'ordine di schedulazione dei threads.

Si consideri, ad esempio, una classe ``ContoBancario`` con il seguente metodo ``preleva``:
```Java
public class ContoBancario {
    private int saldo = 0;
    // ...
    boolean preleva(int somma) {
        if (saldo >= somma) {
            saldo -= somma;
            return true;
        } else return false;
    }
    // ...
}
```
con l'ovvio significato di aggiornare il saldo del conto in seguito ad un prelievo, solo se il saldo non diventa negativo.

Si considerino due threads che eseguono concorrentemente il metodo ``preleva`` sulla stessa istanza di ``ContoBancario`` (``conto``), per la quale l'attributo saldo vale 500.
Supponendo che essi vengano schedulati come segue (caso 1):
| thread 1                 | thread 2                 | Note                                              |
| ------------------------ | ------------------------ | ------------------------------------------------- |
| ``conto.preleva(300);``  | ``conto.preleva(250);``  | i threads chiamano il metodo sul medesimo oggetto |
| ``if(saldo >= somma) {`` | ``if(saldo >= somma) {`` | in entrambi i casi la condizione è vera           |
| ``saldo -= somma;``      | sospeso                  | ``saldo`` diviene 200 = 500 - 300                 |
| sospeso                  | ``saldo -= somma;``      | ``saldo`` diviene -50 = 200 - 250                 |
| ``return true;``         | ``return true;``         | in entrambi i casi viene restituito vero          |
Ma se venissero schedulati così (caso 2):
| thread 1                 | thread 2                 | Note                                              |
| ------------------------ | ------------------------ | ------------------------------------------------- |
| ``conto.preleva(300);``  | ``conto.preleva(250);``  | i threads chiamano il metodo sul medesimo oggetto |
| ``if(saldo >= somma) {`` | sospeso                  | la condizione è vera per thread 1                 |
| ``saldo -= somma;``      | sospeso                  | ``saldo`` diviene 200 = 500 - 300                 |
| ``return true;``         | sospeso                  | thread 1 restituisce vero                         |
| altre istruzioni...      | ``if(saldo >= somma) {`` | la condizione è falsa per thread 2                |
| altre istruzioni...      | ``return false;``        | thread 2 restituisce falso                        |
il risultato sarebbe completamente diverso:
| caso | valori restituiti | saldo finale |
| ---- | ----------------- | ------------ |
| 1    | true, true        | -50          |
| 2    | true, false       | 200          |
| x    | false, true       | 250          |
In questo caso, l'anomalia si può risolvere impedendo che il metodo ``preleva`` possa essere eseguito contemporaneamente da più di un thread cioé garantendo la *mutua esclusione* nella sua esecuzione.

In Java, questo si ottiene dichiarando il metodo ``synchronized``:
```Java
synchronized boolean preleva(int somma) {
```
>La JVM garantisce che un solo thread alla volta possa eseguire del codice synchronized di un oggetto cosicché, se un thread ottiene il diritto di eseguire il codice di un metodo synchronized, finché questo non ha terminato l'esecuzione del metodo altri threads che volessero eseguire un qualunque metodo synchronized **sul medesimo oggetto** verrebbero messi in attesa.

In sostanza ciò viene realizzato tramite un vincolo esclusivo (unico per ogni oggetto) che viene richiesto dal thread per eseguire il codice e rilasciato al termine dell'esecuzione; se il vincolo non è disponibile (perché assegnato ad un altro thread) il thread viene posto in attesa e riattivato solo quando il vincolo sarà disponibile (il thread che lo possedeva termina l'esecuzione del codice synchronized e rilascia il vincolo).

Ciò garantisce una schedulazione di questo tipo (simile al caso 2):
| thread 1                 | thread 2                 | Note                                              |
| ------------------------ | ------------------------ | ------------------------------------------------- |
| ``conto.preleva(300);``  | ``conto.preleva(250);``  | i threads chiamano il metodo sul medesimo oggetto |
| ...                      | ...                      | l'esecuzione è consentita solo a thread 1         |
| ``if(saldo >= somma) {`` | sospeso                  | la condizione è vera per thread 1                 |
| ``saldo -= somma;``      | sospeso                  | ``saldo`` diviene 200 = 500 - 300                 |
| ``return true;``         | sospeso                  | thread 1 restituisce vero                         |
| ...                      | ...                      | solo ora l'esecuzione è consentita a thread 2     |
| altre istruzioni...      | ``if(saldo >= somma) {`` | la condizione è falsa per thread 2                |
| altre istruzioni...      | ``return false;``        | thread 2 restituisce falso                        |
che fornisce il risultato "coerente" che si otterrebe da un'esecuzione sequenziale.

>Trascurare le possibilità di *race condition* porta a bug molto difficili da riprodurre ed individuare ([Heisenbug](https://it.wikipedia.org/wiki/Heisenbug)).

### Deadlocks
Si ha un *deadlock* quando tutti i threads sono in attesa (circolare) di risorse acquisite da altri threads.

Le condizioni *necessarie* (ma non *sufficienti*) perché si verifichi un *deadlock* sono:
* *Mutual exclusion* (Mutua esclusione): almeno una risorsa deve richiedere la mutua esclusione
* *Hold and wait* (Possesso e attesa): i threads "possiedono" delle risorse e ne attendono altre
* *No preemption* (No prelazione): una risorsa assegnata ad un thread non può essergli forzatamente sottratta
* *Circular wait* (Attesa circolare): ogni thread dev'essere in attesa di una risorsa assegnata ad un altro thread, il quale a sua volta ne attende una assegnata ad un altro, fino a tornare al primo thread

Si consideri, ad esempio, la classe ``ContoBancario`` con i metodi ``preleva`` e ``deposita`` *sincronizzati*, ed un metodo ``trasferisci`` per trasferire un importo da un conto all'altro:
```Java
public class ContoBancario {
    // ...
    synchronized void preleva(int somma) {
        saldo -= somma;
    }
    synchronized void deposita(int somma) {
        saldo += somma;
    }
    synchronized void trasferisci(int somma, ContoBancario destinazione) {
        preleva(somma);
        destinazione.deposita(somma);
    }
    // ...
}
```
con l'ovvio significato di aggiornare il saldo del conto in seguito ad un prelievo o deposito e di effettuare un trasferimento tra conti.

Si considerino due threads che eseguono concorrentemente e trasferiscono una somma tra due conti (``c1`` e ``c2``):
* il primo thread trasferisce la somma 500 da ``c1`` a ``c2``
* il secondo thread trasferisce la somma 200 da ``c2`` a ``c1``

Supponendo che essi vengano schedulati come segue (caso 1):
| thread 1                     | thread 2                     | Note                                                          |
| ---------------------------- | ---------------------------- | ------------------------------------------------------------- |
| ``c1.trasferisci(500, c2);`` | ``c2.trasferisci(200, c1);`` | i 2 threads accedono al metodo (su 2 oggetti!!!)              |
| ``preleva(500);``            | ``preleva(200);``            | i 2 threads accedono al metodo (sull'oggetto già vincolato)   |
| ``c2.deposita(500);``        | ``c1.deposita(200)``         | nessun thread può accedere al metodo ``deposita``...          |
| sospeso                      | sospeso                      | ...perché l'altro thread ha un vincolo esclusivo sull'oggetto |
| sospeso                      | sospeso                      | nessun thread rilascia il vincolo...                          |
| sospeso                      | sospeso                      | ...**DEADLOCK**!!!                                            |

Infatti in questo caso si verificano le condizioni:
* *Mutual exclusion* (Mutua esclusione): ``c1`` e ``c2`` richiedono la mutua esclusione (synchronized!)
* *Hold and wait* (Possesso e attesa): ciascun threads "possiede" un conto e attende l'altro
* *No preemption* (No prelazione): il conto vincolato da un thread non può essergli forzatamente sottratto
* *Circular wait* (Attesa circolare): c1 -> thread 1 -> c2 -> thread 2 -> c1

dove: c -> t significa che c è vincolato da t, t -> c significa che t richiede c.

>Trascurare le possibilità di *deadlock* porta a bug molto difficili da riprodurre ed individuare ([Heisenbug](https://it.wikipedia.org/wiki/Heisenbug)).

### Starvation
Si ha una *starvation* quando uno o più threads (ma non tutti) sono in attesa di risorse e vengono sempre preceduti da altri threads nella loro assegnazione.

In genere questo non accade se l'assegnazione delle risorse è basata su criteri casuali, ma si può verificare quando tali criteri non sono casuali ma basati, ad esempio, sulla priorità dei threads.

