import { IMatrice } from 'app/shared/model/matrice.model';

export interface IUtilisateur {
  id?: number;
  email?: string;
  identifiant?: string;
  matrices?: IMatrice[];
}

export class Utilisateur implements IUtilisateur {
  constructor(public id?: number, public email?: string, public identifiant?: string, public matrices?: IMatrice[]) {}
}
