import { Status } from '../../shared/types/status.interface';

export interface InboxStateInterface {
  statuses: Status[];
  count: number;
}
