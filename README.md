# MVVM
MVVM eli Model-View-ViewModel on arkkitehtuurimalli jossa käyttöliittymä erotetaan sovelluslogiikasta.
Compose sovelluksissa MVVM on hyödyllinen koska compose reafoi suoraan viewmodelin tilaan.
Viewmodel säilyy konfiguraatiomuutoksissa kuten näyttön käännössä. Tämä tekee koodista selkeämpää ja helpompaa.

# StateFlow
StateFlow on Kotlin Coroutines -kirjaston tila-virta, joka edustaa aina yhtä ajankohtaista tilaa.
sillä on arvo jonka pystyy lukemaan value-kentästä. Kun arvo muuttuu, päivitykset tulevat automaattisesti.
Compose kuuntelee StateFlowta ja piirtää UI:n uudelleen, kun tila muuttuu.
