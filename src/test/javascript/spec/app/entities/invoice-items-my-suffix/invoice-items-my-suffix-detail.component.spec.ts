/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LpaecommsTestModule } from '../../../test.module';
import { InvoiceItemsMySuffixDetailComponent } from 'app/entities/invoice-items-my-suffix/invoice-items-my-suffix-detail.component';
import { InvoiceItemsMySuffix } from 'app/shared/model/invoice-items-my-suffix.model';

describe('Component Tests', () => {
    describe('InvoiceItemsMySuffix Management Detail Component', () => {
        let comp: InvoiceItemsMySuffixDetailComponent;
        let fixture: ComponentFixture<InvoiceItemsMySuffixDetailComponent>;
        const route = ({ data: of({ invoiceItems: new InvoiceItemsMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [InvoiceItemsMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InvoiceItemsMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvoiceItemsMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.invoiceItems).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
