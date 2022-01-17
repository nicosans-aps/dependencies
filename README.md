# dependencies

Documentation des dépendances

- Parser les livrables pour recueillir toutes les librairies livrées.
- Obtenir un graphe de dépendances.
- Comparer les dépendances identifiées à un référentiel pour les mapper à des propriétés supplémentaires (license par exemple)

L'identification des dépendances à l'aide des noms des jar n'est pas fiable.

L'outil doit pouvoir être lancé par Gradle, mais également de manière autonome.

L'outil jdeps présent dans le jdk à partir de java 8 est indépendant de Gradle.
