import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMatrice } from 'app/shared/model/matrice.model';

type EntityResponseType = HttpResponse<IMatrice>;
type EntityArrayResponseType = HttpResponse<IMatrice[]>;

@Injectable({ providedIn: 'root' })
export class MatriceService {
  public resourceUrl = SERVER_API_URL + 'api/matrices';

  constructor(protected http: HttpClient) {}

  create(matrice: IMatrice): Observable<EntityResponseType> {
    return this.http.post<IMatrice>(this.resourceUrl, matrice, { observe: 'response' });
  }

  update(matrice: IMatrice): Observable<EntityResponseType> {
    return this.http.put<IMatrice>(this.resourceUrl, matrice, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatrice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatrice[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
