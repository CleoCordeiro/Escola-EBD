import dayjs from 'dayjs';
import { ITelefone } from 'app/shared/model/telefone.model';
import { IEndereco } from 'app/shared/model/endereco.model';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';

export interface IResponsavel {
  id?: string;
  nome?: string;
  cpf?: string;
  sexo?: Sexo;
  parentesco?: string;
  dataCadastro?: string | null;
  telefones?: ITelefone[] | null;
  enderecos?: IEndereco[] | null;
  alunos?: IPessoa[] | null;
}

export const defaultValue: Readonly<IResponsavel> = {};
