import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PersonModel } from '../../model/Person';
import { Observable } from 'rxjs';
import { Pagination } from '../../model/Pagination';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http:HttpClient) { }
  private baseUrl = 'http://localhost:8080/api/person';

  getAllPeople(): Observable<PersonModel[]> {
    return this.http.get<PersonModel[]>(this.baseUrl);
  }

  getPersonModel(document: string): Observable<PersonModel> {
    return this.http.get<PersonModel>(`${this.baseUrl}/${document}`);
  }

  createPersonModel(PersonModel: PersonModel): Observable<string> {
    return this.http.post<string>(this.baseUrl, PersonModel);
  }

  updatePersonModel(document: string, PersonModel: PersonModel): Observable<string> {
    return this.http.put<string>(`${this.baseUrl}/${document}`, PersonModel);
  }

  deletePersonModel(document: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${document}`);
  }

  public getAllPeoplePaginated(paginacion: Pagination){
    return this.http.post<any>(this.baseUrl+'/filterPerson', paginacion)
  }
}
