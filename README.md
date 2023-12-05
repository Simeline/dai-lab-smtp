# Rapport laboratoire n°4 - SMTP

# Introduction

L'objectif de ce laboratoire est de mettre en place un client SMTP qui envoie des pranks à une liste de victimes.
L'utilisateur doit fournir une liste de victimes et une liste de messages à envoyer.

Pour tester que les messages SMTP soient correctement envoyés, nous avons mis en place un serveur Mock SMTP.
Ce serveur a été déployé en utilisant l'outil Docker. Pour le lancer, il faut exécuter, dans le terminal, la commande :

```
    docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev
```

Puis il suffit de lancer l'URL suivant pour visualiser le serveur Mock SMTP sur l'interface web :

```
    http://localhost:1080
```

# MailDev

## Description

[MailDev](https://github.com/maildev/maildev) est un serveur de simulation qui appartient à la catégorie des outils
permettant de reproduire
le comportement d'un vrai service SMTP. Cette approche évite de surcharger un serveur SMTP en production,
ce qui pourrait entraîner des problèmes de blacklistage.

# Client SMTP

## Configuration

Le client SMTP se configure en deux étapes :

- Etablir la liste de victimes et la liste de messages
- Déterminer les différentes valeurs de configuration dans le fichier
  de [Configuration](src/main/java/ch/heig/dai_lab_smtp/Configuration.java)

### Liste de victimes et liste de messages

Afin de pouvoir envoyer une grande quantité de mails dans un laps de temps court, il est nécessaire d'établir deux
listes : la liste des adresses mail des victimes ainsi que la liste des messages qui peuvent leur être envoyés.

Les adresses mail des victimes sont placées dans un premier fichier, au format texte. Chaque adresse mail sera soumise à
une validation
à l'aide d'une expression régulière, afin de vérifier qu'elle possède un format valide.
Les messages, quant à eux, sont placés dans un autre fichier, au format json cette fois. Chaque message doit être
composé d'un sujet et d'un corps de message.

Pour plus de simplicité, il est conseillé de placer ces deux fichiers dans le
répertoire [resources](src/main/resources).

### Autres valeurs de configuration

Dans la classe Configuration, plusieurs valeurs sont à renseigner. Il faudra choisir le nombre de groupes (nombre
supérieur à 0) ainsi que le nombre de personnes par groupes, en comptant l'expéditeur. La taille d'un groupe doit être
d'un minimum de 2 personnes et d'un maximum de 5 personnes. Ces informations détermineront le nombre de victimes du
Prank.

D'autres informations sont configurables dans cette classe, telles que le numéro de port du serveur ou les chemins
(absolus ou relatifs) des deux fichiers préparés au point précédent.

## Utilisation

Une fois toutes les configurations réalisées, deux options sont disponibles pour le lancement de l'application:
directement depuis l'IDE ou via un fichier JAR. Dans les deux cas, il sera alors possible d'observer les mails envoyés
via l'interface web offerte par Maildev.

### Lancement depuis l'IDE

Cette manière de procéder est la plus simple, mais peut varier d'un IDE à l'autre. Une fois le serveur mail lancé dans
docker, il suffit de lancer l'exécution de la classe [Main](src/main/java/ch/heig/dai_lab_smtp/Main.java).

A titre informatif, le code a été rédigé en Java via un IDE tel qu'IntelliJ IDEA.

### Lancement depuis un fichier JAR

Afin de pouvoir lancer l'application de cette manière, il est nécessaire d'en faire un package, à l'aide de la
commande suivante :

```
mvn clean compile assembly:single
```

Une fois fait, l'application est exécutable avec la commande :

```
java -jar .\target\SMTP_Prank-1.0-jar-with-dependencies.jar
```

Aucun argument n'est nécessaire, puisque toute la configuration se fait via le fichier de configuration.

## Implémentation

Pour comprendre la présente implémentation, vous pouvez déchiffrer le diagramme UML suivant.

![Diagramme UML](src/main/figures/Diagramme.png)

Le code se décompose en 4 parties distinctes :

- **Main** : Cette classe permet le déroulement principal de l'application. On y itère pour générer les Prank et envoyer
  les messages.
- **ReadFiles** : Cette classe s'occupe de récupérer les informations des fichiers de ressources (victimes et messages).
  Une classe interne permet également une utilisation plus simple des différentes parties d'un message.
- **Prank** : Cette classe s'occupe de préparer les différents composants nécessaires, à savoir l'expéditeur, les
  destinataires et le message. Une méthode permet également de générer une nouvelle combinaison de ces valeurs.
- **SMTPCLient** : Cette classe est l'élément central de notre code, à savoir l'expédition, via un socket, du message aux
  différents destinataires.

## Exemple d'échange

Cette partie représente les messages du serveur suite à l'envoi d'un mail par le client.
```
    (S): 220 1e19ce8517f9 ESMTP
    (C): EHLO localhost
    (S): 250-PIPELINING
    (S): 250-8BITMIME
    (S): 250 SMTPUTF8
    (C): MAIL FROM: <calvin.graf@heig-vd.ch>
    (S): 250 Accepted
    (C): RCPT TO: <simon.guggisberg@heig-vd.ch>
    (S): 250 Accepted
    (C): RCPT TO: <valentin.bonzon@heig-vd.ch>
    (S): 250 Accepted
    (C): DATA
    (S): 354 End data with <CR><LF>.<CR><LF>
    (C): Content-Type: text/plain; charset=UTF-8
    (C): Subject: Pour notre amitié sans faille
    (C): From: <calvin.graf@heig-vd.ch>
    (C):
    (C): Salut,

    Je voulais juste prendre un moment pour te dire à quel point notre amitié compte pour moi. Tu es comme le code source de ma vie, apportant de la joie, de la stabilité et une tonne de rires. Nos moments partagés sont comme des fonctions bien définies : ils rendent ma vie beaucoup plus facile et plus amusante.

    Chaque fois que je pense à notre amitié, je me rends compte à quel point tu es une constante précieuse dans ma vie. Tu es le point virgule à mes phrases, la virgule à mes listes de moments heureux et le point d'exclamation à mes jours ordinaires.
    
    Merci d'être toujours là pour moi, même dans les moments les plus turbulents. J'apprécie chaque ligne de code que nous avons écrite ensemble dans le grand livre de notre amitié.
    
    À toutes nos aventures passées et à venir !
    (C):
    .
    
    (C): QUIT
```