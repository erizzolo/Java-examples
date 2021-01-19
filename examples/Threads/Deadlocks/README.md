## Deadlocks

Il più semplice esempio di deadlocks con due threads e due oggetti.

Come risolvere: ...

Esempi di soluzioni:
* lock statico (elimina *Hold and wait*)
* priorità delle risorse (elimina *Circular wait*)

### Lock statico
In questo caso il metodo è statico e quindi il thread che esegue un trasferimento (qualunque siano i conti) è uno soltanto.

Le prestazioni diminuiscono perchè trasferimenti che riguardano due *diversi* conti sono anch'essi bloccati.

### Priorità delle risorse
In questo caso il metodo acquisisce le risorse secondo un ordine (arbitrario) di priorità.

Ciò garantisce che non vi siano situazioni di attesa circolare.


