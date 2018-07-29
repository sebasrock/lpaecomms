/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LpaecommsTestModule } from '../../../test.module';
import { InvoiceItemsMySuffixUpdateComponent } from 'app/entities/invoice-items-my-suffix/invoice-items-my-suffix-update.component';
import { InvoiceItemsMySuffixService } from 'app/entities/invoice-items-my-suffix/invoice-items-my-suffix.service';
import { InvoiceItemsMySuffix } from 'app/shared/model/invoice-items-my-suffix.model';

describe('Component Tests', () => {
    describe('InvoiceItemsMySuffix Management Update Component', () => {
        let comp: InvoiceItemsMySuffixUpdateComponent;
        let fixture: ComponentFixture<InvoiceItemsMySuffixUpdateComponent>;
        let service: InvoiceItemsMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [InvoiceItemsMySuffixUpdateComponent]
            })
                .overrideTemplate(InvoiceItemsMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvoiceItemsMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceItemsMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InvoiceItemsMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.invoiceItems = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InvoiceItemsMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.invoiceItems = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
