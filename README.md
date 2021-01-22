# TP_L4
Trilma
Autorzy: Kacper Krasnopolski, Piotr Żuk

Kompilowano przy użyciu javac z pakietu jdk-13.0.2
Testy wykonywano przy użyciu JUnit 4

Uruchamianie: 
W katalogu Trylma należy wywołać polecenie mvn eclipse:eclipse oraz zaimportować gotowy projekt do Eclipsa.
Aby rozpocząć rozgrywkę należy uruchomić connection.TrilmaServer, podając w linii komend liczbę graczy (2, 3, 4 lub 6),
a następnie uruchomić odpowiednią liczbę aplikacji connection.TrilmaClient. Piony przemieszczamy myszką.

Testy:
Aby przeprowadzić testy (aktualnie tylko klasa Trylma.GameRulesTest), należy najpierw uruchomić TrilmaServer z parametrem 2 (2 graczy), a następnie GameRulesTest. 

Dodatkowe:
W archiwum znajdują się dodatkowo dwa diagramy: klas i aktywności, odpowiednio class_diagram.drawio i activity_diagram.drawio,
do otwarcia np. przy użyciu edytora ze strony app.diagrams.net. W folderze Trylma/doc znajduje się dokumentacja wygenerowana
w javadocu.
