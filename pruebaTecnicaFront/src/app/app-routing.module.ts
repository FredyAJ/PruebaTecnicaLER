import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PersonListComponent } from './Components/person-list/person-list.component';
import { PersonFormComponent } from './Components/person-list/person-form/person-form.component';

const routes: Routes = [
  {
    path: '', component: PersonListComponent,

  },
  { path: 'createPerson', component: PersonFormComponent },
  {
    path:'editPerson/:person', component: PersonFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
