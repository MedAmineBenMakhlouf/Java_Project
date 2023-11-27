import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UsersService } from 'src/app/services/user.service';

@Component({
  selector: 'app-show-one',
  templateUrl: './show-one-user.component.html',
  styleUrls: ['./show-one-user.component.scss']
})
export class ShowOneUserComponent {
  user?: any;

  constructor(
    private userService: UsersService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id']; // Get ID from route parameters
      this.userService.getOneUser(id).subscribe({
        
      next: (user) => {
        console.log(JSON.stringify(user));
        
        this.user = user;

      },
      error: (error) => {
        console.error('There was an error!', error);
      }
      
    });
    });
  }
}
