import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategorieTache } from 'app/shared/model/categorie-tache.model';

type EntityResponseType = HttpResponse<ICategorieTache>;
type EntityArrayResponseType = HttpResponse<ICategorieTache[]>;

@Injectable({ providedIn: 'root' })
export class CategorieTacheService {
  public resourceUrl = SERVER_API_URL + 'api/categorie-taches';

  constructor(protected http: HttpClient) {}

  create(categorieTache: ICategorieTache): Observable<EntityResponseType> {
    return this.http.post<ICategorieTache>(this.resourceUrl, categorieTache, { observe: 'response' });
  }

  update(categorieTache: ICategorieTache): Observable<EntityResponseType> {
    return this.http.put<ICategorieTache>(this.resourceUrl, categorieTache, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieTache>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieTache[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
