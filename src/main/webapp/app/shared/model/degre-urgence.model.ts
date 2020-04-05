import { ITache } from 'app/shared/model/tache.model';

export interface IDegreUrgence {
  id?: number;
  ordre?: number;
  libelle?: string;
  taches?: ITache[];
}

export class DegreUrgence implements IDegreUrgence {
  constructor(public id?: number, public ordre?: number, public libelle?: string, public taches?: ITache[]) {}
}
