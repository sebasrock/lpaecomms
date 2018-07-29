import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IInvoiceItemsMySuffix } from 'app/shared/model/invoice-items-my-suffix.model';
import { InvoiceItemsMySuffixService } from './invoice-items-my-suffix.service';
import { IStockMySuffix } from 'app/shared/model/stock-my-suffix.model';
import { StockMySuffixService } from 'app/entities/stock-my-suffix';
import { IInvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';
import { InvoicesMySuffixService } from 'app/entities/invoices-my-suffix';

@Component({
    selector: 'jhi-invoice-items-my-suffix-update',
    templateUrl: './invoice-items-my-suffix-update.component.html'
})
export class InvoiceItemsMySuffixUpdateComponent implements OnInit {
    private _invoiceItems: IInvoiceItemsMySuffix;
    isSaving: boolean;

    stocks: IStockMySuffix[];

    invoices: IInvoicesMySuffix[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private invoiceItemsService: InvoiceItemsMySuffixService,
        private stockService: StockMySuffixService,
        private invoicesService: InvoicesMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invoiceItems }) => {
            this.invoiceItems = invoiceItems;
        });
        this.stockService.query().subscribe(
            (res: HttpResponse<IStockMySuffix[]>) => {
                this.stocks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.invoicesService.query().subscribe(
            (res: HttpResponse<IInvoicesMySuffix[]>) => {
                this.invoices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.invoiceItems.id !== undefined) {
            this.subscribeToSaveResponse(this.invoiceItemsService.update(this.invoiceItems));
        } else {
            this.subscribeToSaveResponse(this.invoiceItemsService.create(this.invoiceItems));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceItemsMySuffix>>) {
        result.subscribe(
            (res: HttpResponse<IInvoiceItemsMySuffix>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackStockById(index: number, item: IStockMySuffix) {
        return item.id;
    }

    trackInvoicesById(index: number, item: IInvoicesMySuffix) {
        return item.id;
    }
    get invoiceItems() {
        return this._invoiceItems;
    }

    set invoiceItems(invoiceItems: IInvoiceItemsMySuffix) {
        this._invoiceItems = invoiceItems;
    }
}
