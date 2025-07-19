-- Script d'initialisation de la base de données Foyer
-- Ce script sera exécuté automatiquement lors du premier démarrage du conteneur MySQL

USE foyer_db;

-- Création des tables si elles n'existent pas
-- (Hibernate créera automatiquement les tables grâce à ddl-auto=update)

-- Vous pouvez ajouter ici des données de test si nécessaire
-- INSERT INTO foyer (nom_foyer, capacite_foyer) VALUES ('Foyer A', 100);
-- INSERT INTO bloc (nom_bloc, capacite_bloc) VALUES ('Bloc A', 50); 