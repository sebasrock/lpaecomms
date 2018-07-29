/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LpaecommsTestModule } from '../../../test.module';
import { InvoicesMySuffixDetailComponent } from 'app/entities/invoices-my-suffix/invoices-my-suffix-detail.component';
import { InvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';

describe('Component Tests', () => {
    describe('InvoicesMySuffix Management Detail Component', () => {
        let comp: InvoicesMySuffixDetailComponent;
        let fixture: ComponentFixture<InvoicesMySuffixDetailComponent>;
        const route = ({ data: of({ invoices: new InvoicesMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [InvoicesMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InvoicesMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvoicesMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.invoices).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
