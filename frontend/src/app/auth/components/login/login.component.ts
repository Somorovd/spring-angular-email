import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Store } from '@ngrx/store';

import { combineLatest } from 'rxjs';

import { invalidDomainValidator } from '../../services/invalidDomainValidator';
import { AuthActions } from '../../store/actions';
import { selectErrors, selectIsSubmitting } from '../../store/reducers';
import { LoginRequestInterface } from '../../types/loginRequest.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
})
export class LoginComponent implements OnInit {
  data$ = combineLatest({
    isSubmitting: this.store.select(selectIsSubmitting),
    backendErrors: this.store.select(selectErrors),
  });

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

  constructor(private store: Store, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.store.select(selectErrors).subscribe((errors) => {
      if (!errors) return;
      if (errors['message']) {
        this.formErrors = [...this.formErrors, errors['message']];
      } else {
        this.formErrors = [...this.formErrors, ...Object.values(errors)];
      }
    });
  }

  onSubmit() {
    this.formErrors = [];
    if (!this.form.valid) {
      this.formErrors = this.getFormValidationErrors();
    } else {
      const request: LoginRequestInterface = {
        ...this.form.getRawValue(),
        server: 'dsomorov.xyz',
      };
      this.store.dispatch(AuthActions.login({ request }));
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
