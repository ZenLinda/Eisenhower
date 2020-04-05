import { ITache } from 'app/shared/model/tache.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IMatrice {
  id?: number;
  libelle?: string;
  taches?: ITache[];
  utilisateur?: IUtilisateur;
}

export class Matrice implements IMatrice {
  constructor(public id?: number, public libelle?: string, public taches?: ITache[], public utilisateur?: IUtilisateur) {}
}
