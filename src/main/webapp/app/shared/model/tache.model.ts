import { Moment } from 'moment';
import { IDegreImportance } from 'app/shared/model/degre-importance.model';
import { IDegreUrgence } from 'app/shared/model/degre-urgence.model';
import { IMatrice } from 'app/shared/model/matrice.model';
import { ICategorieTache } from 'app/shared/model/categorie-tache.model';

export interface ITache {
  id?: number;
  libelle?: string;
  descritpion?: string;
  dateIdeale?: Moment;
  dateAlerte?: Moment;
  dateDelai?: Moment;
  degreImportance?: IDegreImportance;
  degreUrgence?: IDegreUrgence;
  matrice?: IMatrice;
  categorie?: ICategorieTache;
}

export class Tache implements ITache {
  constructor(
    public id?: number,
    public libelle?: string,
    public descritpion?: string,
    public dateIdeale?: Moment,
    public dateAlerte?: Moment,
    public dateDelai?: Moment,
    public degreImportance?: IDegreImportance,
    public degreUrgence?: IDegreUrgence,
    public matrice?: IMatrice,
    public categorie?: ICategorieTache
  ) {}
}
