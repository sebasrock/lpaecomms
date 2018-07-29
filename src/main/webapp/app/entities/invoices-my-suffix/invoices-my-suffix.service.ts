import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';

type EntityResponseType = HttpResponse<IInvoicesMySuffix>;
type EntityArrayResponseType = HttpResponse<IInvoicesMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class InvoicesMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/invoices';

    constructor(private http: HttpClient) {}

    create(invoices: IInvoicesMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(invoices);
        return this.http
            .post<IInvoicesMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(invoices: IInvoicesMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(invoices);
        return this.http
            .put<IInvoicesMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IInvoicesMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInvoicesMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(invoices: IInvoicesMySuffix): IInvoicesMySuffix {
        const copy: IInvoicesMySuffix = Object.assign({}, invoices, {
            invDate: invoices.invDate != null && invoices.invDate.isValid() ? invoices.invDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.invDate = res.body.invDate != null ? moment(res.body.invDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((invoices: IInvoicesMySuffix) => {
            invoices.invDate = invoices.invDate != null ? moment(invoices.invDate) : null;
        });
        return res;
    }
}
