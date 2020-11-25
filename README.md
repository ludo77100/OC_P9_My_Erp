## Projet P9

* TAUX DE COVERAGE du projet : 78%
        
#### Tests unitaires
Afin de vérifier le coverage on s'appuie sur jacoco
les profiles à activer sont :
    
    - test-business et test-consumer
    

Lancement commande maven :

    - mvn clean verify

Lancement avec jenkins (voir paramétrage dans /doc/jenkinsConfig)

Pour le taux de coverage :

    - soit par votre navigateur en ouvrant les fichiers :
        - myerp-business/target/site/jacoco/index.html
        - myerp-consumer/target/site/jacoco/index.html
        - myerp-model/target/site/jacoco/index.html
    - soit avec jenkins

## Configuration de Jenkins
Une fois Jenkins installé ( https://www.jenkins.io/doc/book/installing/), il est conseillé de  
sauvegarder entre chaque étape.

    1) Créer un nouveau projet freestyle
    2) Dans l'onglet Gestion de code source: cocher Git, et entrer l'URL du repository git
    3) Dans l'onglet Ce qui déclenche le build, cocher Scrutation de l'outil de version  
        et entrer * * * *  
    4) Dans l'onglet Build: Ajouter une étape au build: Invoquer une cible de haut niveau  
        -choisir une version de maven (le plugin maven doit être installé)  
        -enter dans Cibles Maven: clean install -Ptest-business,test-consumer  
     La commande ci-dessus lance un build de maven avec le clean install, -P sert à  
     sélectionner les profils permettant de lancer les tests        
    5) Dans l'onglet Actions à la suite du build: 
        - Ajouter une action après le buid: Publier le rapport des résultats des test JUnit 
        [vérifier que XML des rapports de test contient:**/target/surefire-reports/*.xml  
        et que le Health report amplification factor est à 1.0]  
        - De nouveau ajouter une action après le build: Record Jacoco coverage report  
        [vérifier que Path to exec files (e.g.: **/target/**.exec, **/jacoco.exec) soit à:  
        **/**.exec , que Path to class directories (e.g.: **/target/classDir, **/classes) soit à  
        **/classes , que Path to source directories (e.g.: **/mySourceFiles) soit à: **/src/main/java  ,
        que Inclusions (e.g.: **/*.java,**/*.groovy,**/*.gs) soit à: **/*.java,**/*.groovy,**/*.kt,**/*.kts]
