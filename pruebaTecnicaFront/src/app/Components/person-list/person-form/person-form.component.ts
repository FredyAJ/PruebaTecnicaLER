import { Component, Input, OnInit } from '@angular/core';
import { PersonModel } from '../../../../model/Person';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PersonService } from '../../../../services/Person/person.service';
import { MatDialog } from '@angular/material/dialog';
import { AlertDialogComponent } from '../../alert-dialog-component/alert-dialog-component.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-person-form',
  templateUrl: './person-form.component.html',
  styleUrl: './person-form.component.scss'
})
export class PersonFormComponent implements OnInit {
  ngOnInit(): void {
    this.data = this.route.snapshot.paramMap.get('person') != null ? JSON.parse(this.route.snapshot.paramMap.get('person') ?? '') : null;

    this.form = this.fb.group({
      document: this.data != null ? [this.data.document,] : ['', Validators.required],
      name: this.data != null ? [this.data.name] : [''],
      lastname: this.data != null ? [this.data.lastname] : [''],
      datebirth: this.data != null ? [this.data.datebirth] : ['']
    })
    if (this.data) {
      this.form.get('document')?.disable();
    }
  }
  constructor(private fb: FormBuilder, private personService: PersonService, private dialog: MatDialog, private route: ActivatedRoute, private router: Router,) {

  }
  submitFormulario() {
    if (this.data == null) {
      this.personService.createPersonModel(this.form.value).subscribe((value) => {
        this.dialog.open(AlertDialogComponent, {
          data: {
            message:'se ha inscrito el usuario con cedula: '+ value
          }
        });

      }, (error) => {


        this.dialog.open(AlertDialogComponent, {
          data: {
            message: error.error.fieldErrors[0].message

          }
        });
      })
    } else {
      this.personService.updatePersonModel(this.data.document, this.form.value).subscribe((value) => {
        this.dialog.open(AlertDialogComponent, {
          data: {
            message:'se ha editado el usuario con cedula: '+ value
          }
        });
      }, (error) => {
        this.dialog.open(AlertDialogComponent, {
          data: {
            message: JSON.stringify(error)
          }
        });
      })
    }
  }
  redireccionar(url: string) {
    this.router.navigate(['/']);
    
}

  form: FormGroup = new FormGroup({});
  @Input() data?: PersonModel;

}
