import {Component, OnInit} from '@angular/core';
import {Task} from '../task';
import {TaskService} from '../task.service';
import {log} from 'util';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  taskList: Task[];

  constructor(private service: TaskService) {
  }

  ngOnInit() {
    this.getTaskList();
  }

  delete(id: number) {
    this.service.delete(id).subscribe(value => {
        this.getTaskList();
        log(value);
        return;
      },
      error => log(error.error.message));
  }

  private getTaskList() {
    this.service.getTasks().subscribe((data => {
        this.taskList = data;
      }),
      error => {
        return log(error.error.message);
      });
  }
}
