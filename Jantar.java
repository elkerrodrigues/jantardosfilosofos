class Filosofo extends Thread {
	private Object hashiEsquerdo;
	private Object hashiDireito;

	public Filosofo(Object hashiEsquerdo, Object hashiDireito) {
		this.hashiEsquerdo = hashiEsquerdo;
		this.hashiDireito = hashiDireito;
	}

	private void fazerAcao(String acao) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + acao);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	@Override
	public void run() {
		try {
			while (true) {
				fazerAcao(": Pensando");
				synchronized (hashiEsquerdo) {
					fazerAcao(": Pegou hashi esquerdo");
					synchronized (hashiDireito) {
						fazerAcao(": Pegou hashi direito - comeu");
						fazerAcao(": Soltou o hashi direito");
					}
					fazerAcao(": Soltou o hashi direito.");
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}
}

class Jantar {
	public static void main(String[] args) throws Exception {
		final Filosofo[] filosofos = new Filosofo[5];
		Object[] hashis = new Object[filosofos.length];

		for (int i = 0; i < hashis.length; i++) {
			hashis[i] = new Object();
		}

		for (int i = 0; i < filosofos.length; i++) {
			Object hashiEsquerdo = hashis[i];
			Object hashiDireito = hashis[(i + 1) % hashis.length];

			if (i == filosofos.length - 1) {
				filosofos[i] = new Filosofo(hashiDireito, hashiEsquerdo);
			} else {
				filosofos[i] = new Filosofo(hashiEsquerdo, hashiDireito);
			}
			Thread th = new Thread(filosofos[i], "Filosofo " + (i + 1));
			th.start();
		}
	}
}
