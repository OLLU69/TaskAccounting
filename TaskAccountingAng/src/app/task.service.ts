import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Person, Task} from './task';
import {log} from 'util';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private personsUrl = '/persons';
  private tasksUrl = '/tasks/';
  private persons: Person[];

  constructor(private http: HttpClient) {
    this.http.get<Person  []>(this.personsUrl).subscribe(value => this.persons = value);
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
    return this.http.put<Task>('tasks/' + task.id, task);
  }

  public delete(id: number): Observable<any> {
    return this.http.delete<any>(this.tasksUrl + id);
  }

  public getAllPersons(): Person [] {
    return this.persons;
  }
}
