-- Supprimer les tables si elles existent
DROP TABLE IF EXISTS game_question;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS PLAYER;

-- Créer la table PLAYER
CREATE TABLE PLAYER (
    login VARCHAR(20),
    password VARCHAR(255),
    player_name VARCHAR(255),
    PRIMARY KEY (login)
);

-- Créer la table game
CREATE TABLE game (
    id_game INTEGER NOT NULL AUTO_INCREMENT,
    player_score INTEGER,
    PRIMARY KEY (id_game)
);

-- Créer la table question
CREATE TABLE question (
    id_question INTEGER NOT NULL AUTO_INCREMENT,
    desc_question VARCHAR(255),
    PRIMARY KEY (id_question)
);

-- Créer la table game_question pour lier les jeux et les questions
CREATE TABLE game_question (
    id_game INTEGER,
    id_question INTEGER,
    PRIMARY KEY (id_game, id_question),
    FOREIGN KEY (id_game) REFERENCES game (id_game),
    FOREIGN KEY (id_question) REFERENCES question (id_question)
);

-- Créer la table answer pour les réponses aux questions
CREATE TABLE answer (
    id_answer INTEGER NOT NULL AUTO_INCREMENT,
    desc_answer VARCHAR(255),
    is_correct BOOLEAN,
    id_question INTEGER,
    PRIMARY KEY (id_answer),
    FOREIGN KEY (id_question) REFERENCES question (id_question)
);


INSERT INTO question VALUES(1, "Qu’est ce qu’un VPN ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Vaisseau pour neptune", false, 1);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Réseau privé virtuel", true, 1);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Visual Protection Network", false, 1);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Marque d’ordinateurs", false, 1);

INSERT INTO question VALUES(2, "Quel mot de passe est le plus sécurisé ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("azerty", false, 2);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("azerty1234", false, 2);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("azerty1234!", false, 2);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("@z3rty4321!", true, 2);

INSERT INTO question VALUES(3, "Quel est l'enjeu de la cybersécurité ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Révéler les secrets", false, 3);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Protéger le système d’information", true, 3);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Augmenter les risques pesant sur le système d’information", false, 3);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Rendre difficile la vie des utilisateurs en ajoutant plusieurs contraintes", false, 3);

INSERT INTO question VALUES(4, "Quelle organisation gère la protection des données ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Gendarmerie National", false, 4);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Google", false, 4);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("CNIL", true, 4);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Le groupe Anonymous", false, 4);

INSERT INTO question VALUES(5, "Qu’est ce qu’un Antivirus ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Logiciel pour protéger son ordinateur", true, 5);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Une alternative au vaccin", false, 5);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Logiciel pour pirater", false, 5);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un ordinateur sur protégé", false, 5);

INSERT INTO question VALUES(6, "Qu’est ce qui permet de faire respecter la politique de sécurité du réseau ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Une coque en silicone", false, 6);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un pare feu", true, 6);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Google Drive", false, 6);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Le développeur de l’entreprise", false, 6);

INSERT INTO question VALUES(7, "Lequel est un programme malveillant indépendant qui ne nécessite aucun autre programme ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Porte à piége", false, 7);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Cheval de troie", false, 7);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Ver", true, 7);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Virus", false, 7);

INSERT INTO question VALUES(8, "Qu'est-ce qu'un code incorporé dans un programme pour «exploser» si les conditions sont remplies ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Bombe logique", true, 8);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Porte à piège", false, 8);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Virus", false, 8);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Cheval de troie", false, 8);

INSERT INTO question VALUES(9, "Quel forme de virus est conçue pour éviter la détection par des logiciels antivirus ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Virus de macro", false, 9);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Virus parasite", false, 9);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Virus polymorphe", false, 9);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Virus furtif", true, 9);

INSERT INTO question VALUES(10, "Quel protocole est utilisé pour sécuriser les e-mails ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("POP", false, 10);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("PGP", true, 10);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("SNMP", false, 10);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("HTTP", false, 10);

INSERT INTO question VALUES(11, "Quel est le nombre de sous-clés générées dans l’algorithme IDEA ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("54", false, 11);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("48", false, 11);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("52", true, 11);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("50", false, 11);

INSERT INTO question VALUES(12, "Lequel est un exemple d’algorithme de clé publique ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("RSA", true, 12);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("DES", false, 12);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("IREA", false, 12);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("RC5", false, 12);

INSERT INTO question VALUES(13, "Qu’est ce qui transforme le message en format illisible par les pirates ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Décryptage", false, 13);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Cryptage", true, 13);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Transformation", false, 13);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Suppression", false, 13);

INSERT INTO question VALUES(14, "Quel est le numéro de port pour HTTPS (HTTP Secure) ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("443", true, 14);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("404", false, 14);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("43", false, 14);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("445", false, 14);

INSERT INTO question VALUES(15, "Quel est la méthode utilisée pour valider l’identité de l’expéditeur d’un message ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Selfie", false, 15);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Décryptage", false, 15);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Certificat numérique", true, 15);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Formule de politesse à la fin", false, 15);

INSERT INTO question VALUES(16, "Qu’est ce qu’un hacker ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un pirate informatique", true, 16);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un biscuit aperitif", false, 16);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un virus surpuissant", false, 16);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Une clé usb pour écouter des conversations", false, 16);

INSERT INTO question VALUES(17, "Qu’est ce qu’un pentest ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un stylo connecté", false, 17);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un test de débit réseau", true, 17);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Evaluation de la sécurité d’un système d’information", false, 17);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un virus pour pénétrer la base de donnée", false, 17);

INSERT INTO question VALUES(18, "Qu’est ce que le RGDP ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Règlement général sur la protection des données", true, 18);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Règle globale pour le piratage des données", false, 18);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Un format de fichier crypté", false, 18);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Regular guaranteed data protection", false, 18);

INSERT INTO question VALUES(19, "Quelle est la perte moyenne de CA suite à une attaque ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("13%", false, 19);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("43%", false, 19);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("80%", false, 19);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("27%", true, 19);

INSERT INTO question VALUES(20, "Que signifie DDOS ?");
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Attaque par déni de service", true, 20);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Data Destructor on system", false, 20);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Force secrète militaire", false, 20);
INSERT INTO answer(desc_answer, is_correct, id_question) VALUES("Logiciel Antivirus", false, 20);

