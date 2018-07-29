/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LpaecommsTestModule } from '../../../test.module';
import { ClientsMySuffixUpdateComponent } from 'app/entities/clients-my-suffix/clients-my-suffix-update.component';
import { ClientsMySuffixService } from 'app/entities/clients-my-suffix/clients-my-suffix.service';
import { ClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';

describe('Component Tests', () => {
    describe('ClientsMySuffix Management Update Component', () => {
        let comp: ClientsMySuffixUpdateComponent;
        let fixture: ComponentFixture<ClientsMySuffixUpdateComponent>;
        let service: ClientsMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [ClientsMySuffixUpdateComponent]
            })
                .overrideTemplate(ClientsMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClientsMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientsMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClientsMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clients = entity;
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
                    const entity = new ClientsMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clients = entity;
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
