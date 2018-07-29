import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IInvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';
import { InvoicesMySuffixService } from './invoices-my-suffix.service';
import { IClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';
import { ClientsMySuffixService } from 'app/entities/clients-my-suffix';

@Component({
    selector: 'jhi-invoices-my-suffix-update',
    templateUrl: './invoices-my-suffix-update.component.html'
})
export class InvoicesMySuffixUpdateComponent implements OnInit {
    private _invoices: IInvoicesMySuffix;
    isSaving: boolean;

    clients: IClientsMySuffix[];
    invDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private invoicesService: InvoicesMySuffixService,
        private clientsService: ClientsMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invoices }) => {
            this.invoices = invoices;
        });
        this.clientsService.query().subscribe(
            (res: HttpResponse<IClientsMySuffix[]>) => {
                this.clients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.invoices.invDate = moment(this.invDate, DATE_TIME_FORMAT);
        if (this.invoices.id !== undefined) {
            this.subscribeToSaveResponse(this.invoicesService.update(this.invoices));
        } else {
            this.subscribeToSaveResponse(this.invoicesService.create(this.invoices));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInvoicesMySuffix>>) {
        result.subscribe((res: HttpResponse<IInvoicesMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackClientsById(index: number, item: IClientsMySuffix) {
        return item.id;
    }
    get invoices() {
        return this._invoices;
    }

    set invoices(invoices: IInvoicesMySuffix) {
        this._invoices = invoices;
        this.invDate = moment(invoices.invDate).format(DATE_TIME_FORMAT);
    }
}
