import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructionBoxComponent } from './instruction-box.component';

describe('InstructionBoxComponent', () => {
  let component: InstructionBoxComponent;
  let fixture: ComponentFixture<InstructionBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InstructionBoxComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InstructionBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
