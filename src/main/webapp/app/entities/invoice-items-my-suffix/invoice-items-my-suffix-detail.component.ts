import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoiceItemsMySuffix } from 'app/shared/model/invoice-items-my-suffix.model';

@Component({
    selector: 'jhi-invoice-items-my-suffix-detail',
    templateUrl: './invoice-items-my-suffix-detail.component.html'
})
export class InvoiceItemsMySuffixDetailComponent implements OnInit {
    invoiceItems: IInvoiceItemsMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ invoiceItems }) => {
            this.invoiceItems = invoiceItems;
        });
    }

    previousState() {
        window.history.back();
    }
}
