## Banca

Esercizio in cui più threads (*Intestatario*) operano su un unico *ContoBancario*.

Per ogni operazione viene registrato il tipo, l'importo, l'istante e l'intestatario che l'ha effettuata.

Nella classe ``ContoBancario`` i metodi per effettuare le operazioni sono ``synchronized`` per evitare *race conditions*.