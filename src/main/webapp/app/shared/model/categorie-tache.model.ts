import { ITache } from 'app/shared/model/tache.model';
import { Couleur } from 'app/shared/model/enumerations/couleur.model';

export interface ICategorieTache {
  id?: number;
  libelle?: string;
  couleur?: Couleur;
  taches?: ITache[];
}

export class CategorieTache implements ICategorieTache {
  constructor(public id?: number, public libelle?: string, public couleur?: Couleur, public taches?: ITache[]) {}
}
