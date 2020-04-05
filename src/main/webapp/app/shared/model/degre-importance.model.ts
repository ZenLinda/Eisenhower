import { ITache } from 'app/shared/model/tache.model';

export interface IDegreImportance {
  id?: number;
  ordre?: number;
  libelle?: string;
  taches?: ITache[];
}

export class DegreImportance implements IDegreImportance {
  constructor(public id?: number, public ordre?: number, public libelle?: string, public taches?: ITache[]) {}
}
