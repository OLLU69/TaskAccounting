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

  task: Task = new Task();
  persons: CheckedPerson[] = [];
  private addMode = 'add-mode';
  private editMode = 'edit-mode';
  private mode: string = this.addMode;

  constructor(private service: TaskService, private router: Router, private  route: ActivatedRoute) {
  }

  ngOnInit() {
    const id: number = this.route.snapshot.params.id;
    log(id);
    this.setupPersons(id);
  }

  setupPersons(id: number) {
    const persons = this.service.getAllPersons();
    if (persons === undefined) {
      this.persons = [];
      return;
    }
    persons.forEach(value => this.persons.push(new CheckedPerson().set(value)));
    if (id === undefined) {
      this.task = new Task();
      this.mode = this.addMode;
    } else {
      this.service.getTask(id).subscribe(value => {
        this.task = value;
        this.prepareCheckers();
        this.mode = this.editMode;
      });
    }
    log(this.mode);
    log('startDate: ' + this.task.startDate + '; endDate: ' + this.task.endDate);
  }

  private prepareCheckers() {
    this.task.personsList.forEach(person => {
      const checkedPerson = this.persons.find(chPerson => chPerson.id === person.id);
      if (checkedPerson !== undefined) {
        checkedPerson.checked = true;
      }
    });
  }

  saveTask(task: Task) {
    const personsToSave: Person[] = [];
    this.persons.filter(value => value.checked).forEach(value => personsToSave.push(value));
    task.personsList = personsToSave;

    if (this.mode === this.addMode) {
      log(this.addMode);
      this.service.save(task).subscribe(
        value => this.back(),
        error1 => log(error1));
    } else {
      log(this.editMode);
      this.service.update(task).subscribe(
        value => this.back(),
        error1 => log(error1));
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
}
