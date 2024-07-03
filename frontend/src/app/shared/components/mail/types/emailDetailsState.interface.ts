import { Email } from '../../../types/email.interface';
import { Status } from '../../../types/status.interface';

export interface EmailDetailStateInterface {
  status: Status | null;
  emails: Email[];
}
