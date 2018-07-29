import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';

@Component({
    selector: 'jhi-invoices-my-suffix-detail',
    templateUrl: './invoices-my-suffix-detail.component.html'
})
export class InvoicesMySuffixDetailComponent implements OnInit {
    invoices: IInvoicesMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ invoices }) => {
            this.invoices = invoices;
        });
    }

    previousState() {
        window.history.back();
    }
}
