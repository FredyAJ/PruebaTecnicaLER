import { AfterViewInit, Component, Inject, OnInit } from '@angular/core';
import { PersonModel } from '../../../model/Person';
import { CommonModule } from '@angular/common';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { Router, RouterOutlet } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';

import { MatMenuModule } from '@angular/material/menu';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { PersonService } from '../../../services/Person/person.service';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { FilterType, Pagination } from '../../../model/Pagination';
import { PageEvent } from '@angular/material/paginator';


@Component({
  selector: 'app-person-list',
  standalone: false,
  templateUrl: './person-list.component.html',
  styleUrl: './person-list.component.scss'
})
export class PersonListComponent implements OnInit, AfterViewInit {
  setSortColumn(arg0: string) {

    if (this.paginated.columnSort == arg0) {


      this.paginated.columnSort = arg0 + "-1";

    } else {
      this.paginated.columnSort = arg0
    }



    this.find()
  }
  handlePageEvent($event: PageEvent) {
    this.paginated.limit = $event.pageSize
    this.paginated.page = $event.pageIndex
    this.find()
  }
  totalPages: number = 1;
  chageFilterBirthday() {
    if (this.filterBirthday instanceof Date) {
      const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'long', day: 'numeric' };
      const formattedDate: string = this.filterBirthday.toLocaleDateString('en-US', options);
      // Ahora formattedDate tiene el formato 'Day Month Year'

      // Si deseas asignar formattedDate a this.filterBirthday, puedes hacerlo
      this.filterBirthday = formattedDate;
    }

  }
  refreshPPaginated() {
    // Fragmento de código en TypeScript para manejar la entrada del usuario
    // Suponiendo que la entrada proporcionada está en sintaxis de Python

    // Definir una lista para almacenar los filtros de datos
    const dataFilter = [];

    // Verificar si el campo 'document' no está vacío
    if (this.filterDocument || this.filterDocumentType == FilterType.NOT_NULL || this.filterDocumentType == FilterType.IS_NULL) {
      dataFilter.push({
        field: 'document',
        value: this.filterDocument,
        filterType: this.filterDocumentType || 0
      });
    }

    // Verificar si el campo 'name' no está vacío
    if (this.filterName || this.filterNameType == FilterType.NOT_NULL || this.filterNameType == FilterType.IS_NULL) {
      dataFilter.push({
        field: 'name',
        value: this.filterName,
        filterType: this.filterNameType || 0
      });
    }

    // Verificar si el campo 'lastname' no está vacío
    if (this.filterLastName || this.filterLastNameType == FilterType.NOT_NULL || this.filterLastNameType == FilterType.IS_NULL) {
      dataFilter.push({
        field: 'lastname',
        value: this.filterLastName,
        filterType: this.filterLastNameType || 0
      });
    }

    // Convertir el valor de 'filterBirthday' a una cadena
    const birthdayValue = this.filterBirthday.toString();


    // Verificar si el campo 'datebirth' no está vacío
    if (birthdayValue || this.filterBirthdayType == FilterType.NOT_NULL || this.filterBirthdayType == FilterType.IS_NULL) {
      dataFilter.push({
        field: 'datebirth',
        value: birthdayValue,
        filterType: this.filterBirthdayType || 0
      });
    }

    // Si la lista de filtros de datos no está vacía, enviarla

    this.paginated.dataFilter = dataFilter;
    this.find()
  }
  editPerson(_t207: PersonModel) {
    this.router.navigate(['/editPerson', JSON.stringify(_t207)]);
  }
  setfilterBirthday() {


    const startDate = this.range.get('start')!.value;
    const endDate = this.range.get('end')!.value;
    if (startDate != null && endDate != null) {

      const mounthStart = (startDate.getMonth() + 1) < 10 ? '0' + (startDate.getMonth() + 1) : (startDate.getMonth() + 1)
      const mounthEnd = (endDate.getMonth() + 1) < 10 ? '0' + (endDate.getMonth() + 1) : (endDate.getMonth() + 1)
      const formattedStartDate = startDate.getFullYear() + '-' + mounthStart + '-' + startDate.getDate().toString().padStart(2, '0');
      const formattedEndDate = endDate.getFullYear() + '-' + mounthEnd + '-' + endDate.getDate().toString().padStart(2, '0');
      if (startDate && endDate) {
        // Puedes personalizar la lógica del filtro según tus necesidades.
        // En este ejemplo, estoy formateando las fechas como cadenas y las estoy concatenando.
        this.filterBirthday = `${formattedStartDate} -> ${formattedEndDate}`;
        this.refreshPPaginated()
      } else {
        // Si alguna de las fechas no está seleccionada, puedes manejarlo según tus requisitos.

      }

    }

  }
  ngAfterViewInit() {
    // Utiliza setTimeout para retrasar la ejecución del código y evitar el error
    setTimeout(() => {

    });
  }

  setColumns() {
    this.displayedColumns = this.toppings.value ?? [];
    this.displayedColumns.push("options")


  }
  deleteColumn(arg0: string) {
    const index = this.displayedColumns.indexOf(arg0); // Encuentra el índice del elemento a eliminar
    if (index !== -1) {
      this.displayedColumns.splice(index, 1); // Elimina el elemento del array
    }
  }

  displayedColumns: string[] = [
    'document',
    'name',
    'lastname',
    'datebirth',
    'options'
  ]
  toppings = new FormControl(this.displayedColumns.slice(0, this.displayedColumns.length - 1));

  filterDocument = '';
  filterDocumentType = 0;


  filterName = '';
  filterNameType = 0;



  filterLastName = '';
  filterLastNameType = 0;


  filterBirthday: string | Date | string[] | Date[] = '';
  filterBirthdayType = 0;

  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
  basedisplayedColumns: any[] = [
    {
      value: "document",
      label: "Cedula"
    },
    {
      value: "name",
      label: "Nombre"
    },
    {
      value: "lastname",
      label: "Apellido"
    },
    {
      value: "datebirth",
      label: "Fecha de Nacimiento"
    }

  ]
  dataSource: PersonModel[] = [];
  paginated: Pagination;
  constructor(private personServise: PersonService,
    private router: Router) {
    this.paginated = {
      limit: 5,
      page: 0,
      dataFilter: [],
      columnSort: ''
    }
  }

  ngOnInit(): void {

    this.find()

  }
  find() {
    this.personServise.getAllPeoplePaginated(this.paginated).subscribe((value) => {
      this.dataSource = value['content']

      this.totalPages = value['totalElements']



    })
  }
  public deleteByDocument(document: string) {
    this.personServise.deletePersonModel(document).subscribe((data: any) => {

      this.find()
    })

  }

}
