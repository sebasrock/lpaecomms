import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockMySuffix } from 'app/shared/model/stock-my-suffix.model';

type EntityResponseType = HttpResponse<IStockMySuffix>;
type EntityArrayResponseType = HttpResponse<IStockMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class StockMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/stocks';

    constructor(private http: HttpClient) {}

    create(stock: IStockMySuffix): Observable<EntityResponseType> {
        return this.http.post<IStockMySuffix>(this.resourceUrl, stock, { observe: 'response' });
    }

    update(stock: IStockMySuffix): Observable<EntityResponseType> {
        return this.http.put<IStockMySuffix>(this.resourceUrl, stock, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStockMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStockMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
