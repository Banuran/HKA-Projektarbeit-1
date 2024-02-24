# Parallelität in Java

In dieser Arbeit werden verschiedene Threadtypen, die in Java bereitgestellt werden, verglichen. Dazu wird ein Programm geschrieben, dass mehrere Threads startet und die benötigte Zeit misst, bis alle Threads beendet wurden. Anhand dieser Messgröße wird die Performance der verschiedenen Threadtypen verglichen.

## Erläuterung der verschiedenen Threadtypen

## Erläuterung des Programms

## Auswertung

Um die Ergebnisse auszuwerten, wird die exportierte CSV-Datei in ein Python Jupyter Notebook eingelesen. Die Daten werden dann in einem Diagramm visualisiert. Für jeden Threadtyp wird eine eigene Kurve in das Diagramm eingezeichnet. Auf der x-Achse werden die Durchläufe anhand der Anzahl der gestarteten Threads abgetragen. Da bei den höheren Werten zunehmend größere Abstände vorhanden sind, wird die x-Achse der besseren Übersichtlichkeit wegen, zunächst logarithmisch skaliert. Auf der y-Achse wird die ermittelte durchschnittliche Laufzeit abgetragen.

![Vergleich aller Threadtypen](images/System1_r1_all_log.png)

In diesem Diagramm ist deutlich zu sehen, dass die benötigte Laufzeit bei den Betriebssystemthreads (blau) sehr viel schneller ansteigt, als bei den anderen beiden Typen. Dadurch sind die Unterschiede zwischen den anderen Typen kaum sichtbar. Lediglich bei der höchsten Anzahl an Threads ist erkennbar, dass die Kurven auseinanderlaufen. Daher werden als nächstes nur die virtuellen Threads und die Threadpools betrachtet.

![Vergleich zwischen Virtual und Pooled Threads](images/System1_r1_part_log.png)

Betrachtet man nur die Kurven zu virtuellen Threads und zu den Threadpools können die Unterschiede zwischen diesen besser erkannt werden. Die Performanceunterschiede zwischen diesen beiden Threadtypen sind jedoch weiterhin gering. Bei einer großen Anzahl an Threads liegt der Unterschied zwischen diesen bei mehreren hundert Millisekunden. Der Unterschied zwischen den Typen nimmt mit steigender Anzahl von Threads zu. Hier haben Threadpools (orange) Vorteile und weisen die geringere Laufzeit auf.

![Ausschnitt aus dem Vergleich zwischen Virtual und Pooled Threads](images/System1_r1_part_short.png)
*abc*

Betrachtet man einen kleineren Ausschnitt des Diagramms, kann man sehen, dass dies nicht für alle Messpunkte gilt.

## Aufgetretene Schwierigkeiten

nur kleiner Ausschnitt betrachtet, angenommen Unterschied liegt in Initialisierung

Graal VM: Besondere prompt notwendig, falsche Rechte wegen USB-Stick: musste erst rüber kopiert werden und dann auf ausführbar gesetzt werden

## Fazit