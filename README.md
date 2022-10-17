#   RPGMod 
## Items ajoutés
---
### Matériaux :
> #### Lingot d'acier
> *Utilité: Materiel*
>##### **Recettes:**
>![Recette](images/image4.png)
><br>![Recette](images/image24.png)

> #### Lingot de titane
> *Utilité: Materiel*
> ##### **Recettes:**
> ![Recette](images/image50.png)
> <br>![Recette](images/image33.png)


> #### Lingot de titane renforcé
> *Utilité: Materiel*
> ##### **Recettes:**
> ![Recette](images/image31.png)
> <br>![Recette](images/image9.png)

> #### Matériaux compressés
> [**Recettes**](Docs/MateriauxCompresses.md)

 ### Outils:

> #### Outils en cuivre
> *Utilité: Outils*
><br/>[**Recettes**](Docs/OutilsEnCuivre.md)

> #### Outils en acier
> *Utilité: Outils*
><br/>[**Recettes**](Docs/OutilsEnAcier.md)

> #### Outils en titane
> *Utilité: Outils*
><br/>[**Recettes**](Docs/OutilsEnTitane.md)



> #### Outils en titane renforcé
> *Utilité: Outils*
><br/>[**Recettes**](Docs/OutilsEnTitaneRenforce.md)




### Épées spéciales
#### Épées élémentaires
*Font des dégâts élémentaires aux mobs. Si le mob est résistant à l'élément, il ne va pas ressentir les effets. S’il est vulnérable, il ressentira soit des effets supplémentaires, soit l'effet sera amplifié*.
>##### Épée de feu
> *Brule les ennemis.*
> ##### **Recette:**
>![Recette](images/image55.png)
>*<br>Utilise le [compresseur a item](#compresseur-a-item)*
    
>##### Épée de glace
>*Gèle les ennemis sur place. Fait des dégâts à ceux vulnérables.* 
>##### **Recette:**
>![Recette](images/image41.png)
>*<br>Utilise le [compresseur a item](#compresseur-a-item)*

>##### Épée de poison
>*Empoisonne les ennemis d'un venin mortel.*
>##### **Recette:**
>![Recette](images/image32.png)
>*<br>Utilise le [compresseur a item](#compresseur-a-item)*
#### Marteau de Thor
*Épée élémentaire spéciale. (Pas de recette pour l'instant)*  

Élément: Éclair
> Fait apparaitre des éclairs sur le mob à chaque 7 Ticks.

> Sur clic droit, font apparaitre des éclairs sur un rayon de 8 blocs autour du joueur. 
#### Épées multiélémentaires
*Épées ayant plusieurs éléments. (Pas de recette pour l'instant)*

>##### **Épée de l'enfer**
>*Éléments:*
>- Poison
>- Feu

>##### **Épée de dieu**
>*Éléments:*
>- Poison
>- Feu
>- Glace
>- Éclair

## Blocs ajoutés
---
### Minerais

*Deux variations: En pierre et en profondoise. Aucune différence autre que le bloc qui drop. Non-craftable.*
> #### Titane
>![Block](images/image65.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Diamant
> <br>Drop: 1x Minerai de titane

> #### Cristal de feu
>![Block](images/image61.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Titane
> <br>Drop: 1-3x cristal de feu

> #### Cristal de glace
>![Block](images/image62.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Titane
> <br>Drop: 1-3x cristal de glace

> #### Cristal de poison
>![Block](images/image63.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Titane
> <br>Drop: 1-3x cristal de poison

> #### Multi cristal
>![Block](images/image64.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Titane renforcé
> <br>Drops: 
>- 1-3x cristal de feu
>- 0-2x cristal de glace
>- 0-2x cristal de poison

### Blocs de métaux

*Blocs craftables faits avec des lingots.*
#### **[Recettes](Docs/BlocsDeMetaux.md)**

>#### Bloc d'acier
>![Block](images/image66.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Cuivre

>#### Bloc de titane
>![Block](images/image68.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Diamant

>#### Bloc de titane renforcé
>![Block](images/image67.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Titane renforcé

### Compresseur à item
>![Block](images/image69.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Titane renforcé
 >#### **Recette:**
 >![Recette](images/image54.png)

#### Fonctionnalité:
*Combine n items selon une recette. Les recettes prennent soit les 9 slots disponibles, soit un seul. Peut prendre de 1-64 item par slot. Requiert de l'énergie pour rouler. Prends 200FE d'énergie par seconde. La capacité maximale est de 60,000 FE et peut être rechargée grâce à du carburant.*

#### Carburants acceptés:
>- Charbon (+10FE)
>- Charbon de bois (+15FE)
>- Bloc de charbon (+95FE)
>- Charbon compressé (+2000FE)
>- Seau de lave (+300FE)

### Table de rareté
>![Block](images/image70.png)
> <br>Outil requis: **Pioche**
> <br>Materiel requis: Titane renforcé
 >#### **Recette:**
 >![Recette](images/image59.png)

#### Fonctionnalité:
*Permet d'augmenter la [rareté](#système-de-raretée) d'un item. Suit une recette. La recette suit un pattern d'item seuls les patterns de 3x3 sont acceptés, mais tous les items peuvent être différents. Prends un item ayant déjà une rareté, puis l'augmente d’un niveau, jusqu'au niveau 5 (Mythique) maximum.*

[**Recettes**](Docs/RecettesTableRarete.md)

## Mécaniques
---
### Mécanique de soif
 ![Soif](images/image71.png)<br>

>*Ajoute une barre de soif. Donne des bonus quand la barre est pleine. À la moitié, le bonus est réduit, puis à partir de 20% le bonus est enlevé. Pour augmenter la barre de soif, il suffit de boire des potions ou des bouteilles d'eau.*
### Indicateur de dommage
![Dommage](images/image72.png)<br>

>*Système simple qui montre la vie restante d'une mob sur la vie totale.*
## Système de rareté
---
### Fonctionnalité:
*Permets de donner des bonus sur les items. 7 niveaux, de commun jusqu'a divin. Plus le niveau est haut, plus les bonus sont hauts.*

### Raretés:
> Commun (Niveau 0)
>
>![commun](images/image57.png)

> Peu commun (Niveau 1)
>
>![peu commun](images/image30.png)

> Rare (Niveau 2)
>
>![rare](images/image5.png)

> Très rare (Niveau 3)
>
>![tres rare](images/image16.png)

> Légendaire (Niveau 4)
>
>![legendaire](images/image40.png)

> Mythique (Niveau 5)
>
>![mythique](images/image36.png)

> Divine (Niveau 6)
>
>![divin](images/image56.png)

## Système de compétence 
---
### Minage
*À chaque bloc miné par une pioche, une quantité d'xp de minage est gagnée. L'xp varie selon le bloc qui est miné dépendant de sa difficulté à miner ainsi que de sa rareté. Après une certaine quantité d'xp accumulé, le niveau de minage augmente. Le palier pour les niveaux augmente a chaque niveau par un certain pourcentage. À tous les quelques level up, un bonus est appliqué.*

>#### Liste des bonus:
>- Tous les 2 niveaux: Le joueur reçoit un point de vie en plus
>- Tous les 10 niveaux: Vitesse de minage amélioré

### Buchage
*À chaque bloc miné par une hache, une quantité d'xp de buchage est gagnée. L'xp varie selon le bloc qui est miné dépendant de si c'est une buche, une planche ou un bloc quelconque. Après une certaine quantité d'xp accumulé, le niveau de buchage augmente. Le palier pour les niveaux augmente à chaque niveau par un certain pourcentage. À tous les quelques level up, un bonus est appliqué.*

>#### Liste des bonus:
>- Tous les 3 niveaux: Le joueur fait plus de dégât d'attaque
>- Tous les 8 niveaux: Jusqu'au niveau 24, le joueur reçoit de la résistance aux dégâts. À partir du niveau 32, le joueur reçoit un bonus de saut. 
### Attaque
*À chaque entité par une épée, une quantité d'xp d'attaque est gagnée. L'xp varie selon le type d'entité qui est tué. Après une certaine quantité d'xp accumulé, le niveau de minage augmente. Le palier pour les niveaux augmente a chaque niveau par un certain pourcentage. À tous les quelques level up, un bonus est appliqué.*

>#### Types d'entités:
>- Inoffensif: Ne fais pas de dégât au joueur
>- Nocif: Peut faire un peu de dégât au joueur / le tuer
>- Dangereux: Grande chance de tuer le joueur s’il n'est pas équipé
>- Très dangereux: Grande chance de tuer le joueur même s'il est bien équipé
>- Boss: Un des boss du jeu. Nécessite de l'équipement très avancé pour battre.

>#### Liste des bonus:
>- Tous les 3 niveaux: Le joueur reçoit de la résistance au recul
>- Tous les 10 niveaux: Jusqu'au niveau 20, le joueur reçoit un bonus de régénération. À partir du niveau 30, le joueur reçoit un bonus de force.
>- Au niveau 30: Le joueur est totalement résistant au feu
>- Au niveau 35: Le joueur peut voir dans le noir

## Système de classe
---
### Fonctionnalité:
*Les classes permettent au joueur de réduire le temps pour améliorer une certaine compétence. À chaque action qui donne de l'xp de compétence, de l'xp de classe est donnée. À chaque niveau de classe obtenu, l'xp de compétence est multiplié par le niveau de la classe.* 
>#### Interface:
>![classes](images/image60.png)

### Classes:

> #### Mineur
>Compétence: Minage
><br>À chaque bloc miné avec une pioche, l'xp de classe est augmenté de 100.

> #### Bucheron
>Compétence: Buchage
><br>À chaque bloc miné avec une hache, l'xp de classe est augmenté de 100.

> #### Soldat
>Compétence: Attaque
><br>À chaque entité tuée avec une épée, l'xp de classe est augmenté de 100.
