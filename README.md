# Les Bijoux du Louvre

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

📌 Présentation

Les Bijoux du Louvre est un jeu 2D développé avec Java et LibGDX, dans lequel vous incarnez un voleur cherchant à dérober un joyau royal… sans se faire repérer par la sécurité du musée.

Vous devrez éviter les lasers, contourner un gardien en patrouille, rester hors du cône de vision, et ramasser les objets précieux avant que le compte à rebours n’atteigne zéro.

✨ Fonctionnalités principales
👤 Voleur

Se déplace librement dans le niveau.

Possède une hitbox elliptique réduite pour coller au sprite.

Collision avec lasers / gardien / vision → défaite immédiate.

🛡️ Gardien

Patrouille sur une zone définie avec aller-retour automatique.

Change de direction après collision avec un laser.

Le cône de vision part directement de sa main et s’oriente selon ses mouvements.

Collision (ou détection) → PERDU !

🔦 Cône de vision dynamique

Triangle semi-transparent avec angle et distance configurables.

Hitbox réduite (shrink factor configurable).

Détection précise grâce à un polygone généré et comparé au voleur.

🔫 Lasers

Lasers verticaux animés.

Possèdent une hitbox rectangulaire distincte du visuel.

Collision pour le gardien : il fait demi-tour.

Collision pour le joueur : défaite.

👑 Objets à collecter

Le voleur peut ramasser la couronne s’il se trouve dans une zone définie.

Ajoute des points au score total.

⏲️ Système de timer

Décompte à partir de 120 secondes.

Passe en rouge à 30 secondes.

Déclenche un son d’alarme lorsqu’il devient critique.

Si le temps expire → écran de défaite.

⏸️ Menu Pause (ESC)

Affichage d’un fond noir semi-transparent.

3 boutons pixel-art :

▶ Reprendre

🔄 Recommencer

⏻ Quitter

Le jeu est totalement gelé pendant la pause.

🧪 Mode Debug (F3)

Utile pour le développement :

Affichage des hitbox :

Voleur (cyan)

Gardien (rouge)

Lasers (vert)

Cône de vision (rouge)

Peut être activé/désactivé sans affecter le gameplay.

🕹️ Commandes
Action	Touche
Déplacements du voleur	Flèches directionnelles
Ramasser un objet	Q
Mettre en pause	ESC
Toggle debug	F3
🛠️ Installation & Exécution
Prérequis

Java 17 ou supérieur

Gradle (ou wrapper fourni)

LibGDX (inclus via gradle)

Lancer le jeu
./gradlew run

📁 Structure principale
/core
  /entities
    Guardian.java
    Voleur.java
  /elements
    Laser.java
    VisionCone.java
  /states
    Test2.java
    PauseState.java
  /managers
    GameStateManager.java
    LaserCollisionManager.java
  /utils
    GameTimer.java

🎨 Assets

Sprites pixel-art pour le voleur, le gardien, les lasers, le sol, les piliers, etc.

Boutons pixel-art pour le menu pause.

Sons : musique du jeu, alarme.

👥 Auteurs

Projet réalisé par l’équipe Les Bijoux du Louvre
dans le cadre du module T-JAV-501.
