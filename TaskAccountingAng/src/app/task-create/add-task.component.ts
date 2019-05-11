import {Component, OnInit} from '@angular/core';
import {CheckedPerson, Person, Task} from '../task';
import {TaskService} from '../task.service';
import {log} from 'util';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-task-create',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit {

  header: string = 'Создание задания';
  task: Task = new Task();
  persons: CheckedPerson[] = [];
  alertMessage: string = null;
  private addMode = 'add-mode';
  private editMode = 'edit-mode';
  private mode: string = this.addMode;

  constructor(private service: TaskService, private router: Router, private  route: ActivatedRoute) {
  }

  ngOnInit() {
    const id: number = this.route.snapshot.params.id;
    log(id);
    this.service.getAllPersons().subscribe(value => {
      return this.setupPersons(id, value);
    });
  }

  setupPersons(id: number, persons: Person[]) {
    if (persons === undefined) {
      this.persons = [];
      return;
    }
    persons.forEach(value => this.persons.push(new CheckedPerson().set(value)));
    if (id === undefined) {
      this.mode = this.addMode;
      this.header = 'Создание задания';
      this.task = new Task();
    } else {
      this.service.getTask(id).subscribe(value => {
        this.mode = this.editMode;
        this.header = 'Редактирование задания';
        this.task = value;
        this.prepareCheckers();
      });
    }
    log(this.mode);
    log('startDate: ' + this.task.startDate + '; endDate: ' + this.task.endDate);
  }

  saveTask(task: Task) {
    const personsToSave: Person[] = [];
    this.showError(null);
    this.persons.filter(value => value.checked).forEach(value => personsToSave.push(value));
    task.personsList = personsToSave;

    if (this.mode === this.addMode) {
      log(this.addMode);
      // noinspection JSUnusedLocalSymbols
      this.service.save(task).subscribe(
        value => this.back(),
        error => {
          log(error.error.message);
          this.showError(error.message)
        });
    } else {
      log(this.editMode);
      // noinspection JSUnusedLocalSymbols
      this.service.update(task).subscribe(
        value => {
          log("Ok");
          this.back();
        },
        error => {
          log(error.error.message);
          this.showError(error.error.message)

        });
    }
  }

  back() {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['/task_list']);
  }

  updateCheck(person: CheckedPerson, i: number) {
    const value = this.persons[i];
    value.checked = !value.checked;
    log('name:' + value.name + ' checked: ' + value.checked);
  }

  private prepareCheckers() {
    this.task.personsList.forEach(person => {
      const checkedPerson = this.persons.find(chPerson => chPerson.id === person.id);
      if (checkedPerson !== undefined) {
        checkedPerson.checked = true;
      }
    });
  }

  private showError(message: string) {
    this.alertMessage = message
  }
}
