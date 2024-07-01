import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ErrorMap } from '../../../shared/types/errorMap.type';
import { invalidDomainValidator } from '../../services/invalidDomainValidator';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
})
export class LoginComponent {
  formErrors: string[] = [];

  form = this.fb.nonNullable.group({
    username: [
      '',
      [
        Validators.required,
        Validators.pattern(/^[a-zA-Z0-9._\-\+@]+$/),
        invalidDomainValidator(),
      ],
    ],
    password: ['', [Validators.required, Validators.minLength(8)]],
  });

  constructor(private fb: FormBuilder) {}

  onSubmit() {
    if (!this.form.valid) {
      this.formErrors = this.getFormValidationErrors();
      return;
    }
  }

  getFormValidationErrors(): string[] {
    const errors: string[] = [];

    const { username, password } = this.form.controls;

    if (username && username.invalid) {
      if (username.errors?.['required']) {
        errors.push('Username is required');
      } else if (username.errors?.['pattern']) {
        errors.push('Username must be alphanumeric or . _ + -');
      } else if (username.errors?.['invalidDomain']) {
        errors.push('Username domain must be dsomorov.xyz');
      }
    }

    if (password && password.invalid) {
      if (password.errors?.['required']) {
        errors.push('Password is required');
      } else if (password.errors?.['minlength']) {
        errors.push('Password must be at least 8 characters');
      }
    }

    return errors;
  }
}
