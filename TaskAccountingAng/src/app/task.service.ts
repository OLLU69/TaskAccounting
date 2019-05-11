import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {Person, Task} from './task';
import {log} from 'util';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private base = 'http://localhost:8080';
  private personsUrl = this.base + '/persons';
  private tasksUrl = this.base + '/tasks/';
  private persons: Person[];

  constructor(private http: HttpClient) {
    this.fetchPersons();
  }

  public getTasks(): Observable<Task[]> {
    log('executes getTasks()');
    return this.http.get<Task[]>(this.tasksUrl)
      .pipe(map(data => {
        return data.map(value => {
          return new Task().setTask(value);
        });
      }));
  }

  public getTask(id: number): Observable<Task> {
    return this.http.get<Task>(this.tasksUrl + id);
  }

  public save(task: Task): Observable<Task> {
    return this.http.post<Task>(this.tasksUrl, task);
  }

  public update(task: Task): Observable<Task> {
    return this.http.put<Task>(this.tasksUrl + task.id, task);
  }

  public delete(id: number): Observable<any> {
    return this.http.delete<any>(this.tasksUrl + id);
  }

  public getAllPersons(): Observable<Person[]> {
    if (this.persons === undefined) {
      return this.fetchPersons()
    }
    return of(this.persons);
  }

  private fetchPersons(): Observable<Person[]> {
    return this.http.get<Person[]>(this.personsUrl).pipe(map(value => {
      this.persons = value;
      return value
    }));
  }
}
