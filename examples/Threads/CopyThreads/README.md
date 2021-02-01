## CopyThreads
### Esempio di collaborazione tra threads per la copia di un file.
Per la copia di un file vengono creati due threads: un *lettore* ed uno *scrittore*.

Il thread *lettore* si occupa di leggere il file originale, trasferendone il contenuto in un buffer.

Il thread *scrittore* si occupa di scrivere il file destinazione, prelevando il contenuto dal buffer.

Per evitare di creare troppi buffer ed utilizzarli una volta soltanto, si usa un pool di buffer che vengono utilizzati a turno dai due threads.

L'accesso ai buffer deve essere opportunamente sincronizzato per garantire una logica corretta.

Se i dispositivi di origine e destinazione sono diversi, ciò garantisce migliori prestazioni (anche in caso di stream non legati ai file).

>Come esercizio, modificate i threads in modo che utilizzino degli *InputStream* o *OutputStream* già creati.

>Se usate Windows, modificate i files [.vscode/settings.json](.vscode/settings.json) e [.vscode/launch.json](.vscode/launch.json) per usare javafx.